package com.vitaly_kuznetsov.point.search.model_layer;

import com.vitaly_kuznetsov.point.base_models.web_socket_api.WebSocketInterface;
import com.vitaly_kuznetsov.point.base_models.web_socket_api.rxwebsocket.RxWebSocket;
import com.vitaly_kuznetsov.point.base_models.web_socket_api.rxwebsocket.entities.SocketFailureEvent;
import com.vitaly_kuznetsov.point.base_models.web_socket_api.rxwebsocket.entities.SocketMessageEvent;
import com.vitaly_kuznetsov.point.search.presenter_layer.WebSocketHandlingInterface;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.vitaly_kuznetsov.point.base_models.web_socket_api.EndPoints.*;

public class SearchWebSocketController implements WebSocketInterface {

    private ArrayList<Disposable> disposables;
    private Disposable messageDisposable;
    private RxWebSocket rxWebSocket;

    private WebSocketHandlingInterface presenter;

    private boolean started = false;

    public SearchWebSocketController(WebSocketHandlingInterface presenter) {
        this.presenter = presenter;
    }

    public void setPresenter(WebSocketHandlingInterface presenter) {
        this.presenter = presenter;
    }

    @Override
    public void start(HashMap<String, String> socketParams){

        disposables = new ArrayList<>();

        rxWebSocket = new RxWebSocket(SOCKET_BASE_URL + SOCKET_SEARCH_ENDPOINT +
                SOCKET_TOKEN + socketParams.get("token") + SOCKET_LONGITUDE +
                socketParams.get("longitude") + SOCKET_LATITUDE + socketParams.get("latitude"));

        disposables.add(rxWebSocket.onOpen().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                subscribe(socketOpenEvent -> {}, Throwable::printStackTrace));

        disposables.add(rxWebSocket.onTextMessage().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                subscribe(this::onMessageReceived, Throwable::printStackTrace));

        disposables.add(rxWebSocket.onClosed().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                subscribe(socketClosedEvent -> {}, Throwable::printStackTrace));

        disposables.add(rxWebSocket.onClosing().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                subscribe(socketClosingEvent -> {}, Throwable::printStackTrace));

        disposables.add(rxWebSocket.onFailure().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                subscribe(this::onErrorOccurred, Throwable::printStackTrace));

        rxWebSocket.connect();

        started = true;
    }

    @Override
    public void stop(){
        if (rxWebSocket != null)
            disposables.add(rxWebSocket.close().subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(success -> System.out.println("WebSocket close initiated. With result: " + success),
                            Throwable::printStackTrace));
        else if (disposables != null) {
            for (Disposable disposable : disposables)
                disposable.dispose();
            disposables = null;
        }
        else
            System.out.println("RxWebSocket == null");

        if (messageDisposable != null)
            messageDisposable.dispose();

        started = false;
    }

    @Override
    public void sendMessage(String message){
        messageDisposable = rxWebSocket.sendMessage(message)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                        subscribe(success -> presenter.onMessageSent(message), Throwable::printStackTrace);
    }

    @Override
    public void onMessageReceived(SocketMessageEvent message){
        if (message.isText()) presenter.onWebSocketResponse(message.getText());
    }

    @Override
    public void onErrorOccurred(SocketFailureEvent failureEvent) {
        presenter.onError(failureEvent.toString());
        started = false;
    }

    public boolean isStarted() {
        return started;
    }
}