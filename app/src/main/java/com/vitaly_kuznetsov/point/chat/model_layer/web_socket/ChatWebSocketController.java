package com.vitaly_kuznetsov.point.chat.model_layer.web_socket;

import com.google.gson.Gson;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.Message;
import com.vitaly_kuznetsov.point.base_models.web_socket_api.WebSocketInterface;
import com.vitaly_kuznetsov.point.base_models.web_socket_api.rxwebsocket.RxWebSocket;
import com.vitaly_kuznetsov.point.base_models.web_socket_api.rxwebsocket.entities.SocketFailureEvent;
import com.vitaly_kuznetsov.point.base_models.web_socket_api.rxwebsocket.entities.SocketMessageEvent;
import com.vitaly_kuznetsov.point.chat.presenter_layer.ChatViewPresenterInterface;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.vitaly_kuznetsov.point.base_models.web_socket_api.EndPoints.*;

public class ChatWebSocketController implements WebSocketInterface {

    private ArrayList<Disposable> disposables;
    private Disposable messageDisposable;
    private RxWebSocket rxWebSocket;

    private boolean started = false;

    private ChatViewPresenterInterface presenter;

    public ChatWebSocketController(ChatViewPresenterInterface presenter) {
        this.presenter = presenter;
    }

    @Override
    public void start(HashMap<String, String> socketParams){

        disposables = new ArrayList<>();

        rxWebSocket = new RxWebSocket(SOCKET_BASE_URL + SOCKET_CHAT_ENDPOINT +
                SOCKET_TOKEN + socketParams.get("token") + SOCKET_YOUR_ID + socketParams.get("yourID"));

        disposables.add(rxWebSocket.onOpen().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                subscribe(socketOpenEvent -> System.out.println("WebSocket opened."), Throwable::printStackTrace));

        disposables.add(rxWebSocket.onTextMessage().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                subscribe(socketMessageEvent -> { System.out.println("Message Received." + socketMessageEvent); onMessageReceived(socketMessageEvent);},
                        Throwable::printStackTrace));

        disposables.add(rxWebSocket.onClosed().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                subscribe(socketClosedEvent -> presenter.onClose(), Throwable::printStackTrace));

        disposables.add(rxWebSocket.onClosing().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                subscribe(socketClosingEvent -> System.out.println("WebSocket is closing."), Throwable::printStackTrace));

        disposables.add(rxWebSocket.onFailure().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                subscribe(socketFailureEvent -> { System.out.println("WebSocket failure! "); onErrorOccurred(socketFailureEvent); }, Throwable::printStackTrace));

        rxWebSocket.connect();

        started = true;
    }

    @Override
    public void stop(){
        started = false;

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
    }

    @Override
    public void sendMessage(String message){
        messageDisposable = rxWebSocket.sendMessage(message)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                        subscribe(success -> System.out.println("Message sent result: " + success), Throwable::printStackTrace);
    }

    @Override
    public void onMessageReceived(SocketMessageEvent message){
        if (message.isText()){
            Gson gson = new Gson();
            Message messageEntity = gson.fromJson(message.getText(), Message.class);
            presenter.onMessageReceived(messageEntity);
        }
    }

    @Override
    public void onErrorOccurred(SocketFailureEvent failureEvent) {
        started = false;
        presenter.onError(failureEvent.toString());
    }

    public boolean isStarted() {
        return started;
    }
}