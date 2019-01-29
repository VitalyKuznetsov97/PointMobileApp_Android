package com.vitaly_kuznetsov.point.search.presenter_layer;

import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BasicModelActionsInterface;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.PostModel;
import com.vitaly_kuznetsov.point.base_models.user_data_model.model.UserDataModel;
import com.vitaly_kuznetsov.point.base_models.user_data_model.user_model_preferences.ModelHandler;
import com.vitaly_kuznetsov.point.base_models.web_socket_api.rxwebsocket.RxWebSocket;
import com.vitaly_kuznetsov.point.search.view_layer.SearchFragment;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchFragmentPresenter implements BasicModelActionsInterface,
                    SearchFragmentPresenterInterface{

    private SearchFragment fragment;
    private UserDataModel userDataModel;

    ArrayList<Disposable> disposables;
    Disposable messageDisposable;
    RxWebSocket rxWebSocket;

    public void attachView(SearchFragment fragment) {
        this.fragment = fragment;
        userDataModel = ModelHandler.getInstance(fragment.getContext());
    }

    public void detachView() {
        fragment = null;
    }

    @Override
    public UserDataModel getUserDataModel() {
        return null;
    }


    @Override
    public void onMainButtonCheckedChanged(boolean checked) {

        if (checked) {
            fragment.startAnimation();

            disposables = new ArrayList<>();

            //"\(SOCKET_URL)/search?token=\(token)&longitude=\(newLocation.longitude)&latitude=\(newLocation.latitude)"
            rxWebSocket = new RxWebSocket("wss://point-backend.com/search?token=cd9bb03a-d4d4-4fd6-bff5-e1133cd0dd76&longitude=1&latitude=1");
            //rxWebSocket = new RxWebSocket("ws://echo.websocket.org");

            disposables.add(rxWebSocket.onOpen().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                    subscribe(socketOpenEvent -> System.out.println("WebSocket opened."), Throwable::printStackTrace));

            disposables.add(rxWebSocket.onTextMessage().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                    subscribe(System.out::println, Throwable::printStackTrace));

            disposables.add(rxWebSocket.onClosed().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                    subscribe(socketClosedEvent -> System.out.println("WebSocket closed."), Throwable::printStackTrace));

            disposables.add(rxWebSocket.onClosing().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                    subscribe(socketClosingEvent -> System.out.println("WebSocket is closing."), Throwable::printStackTrace));

            disposables.add(rxWebSocket.onFailure().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                    subscribe(socketFailureEvent -> System.out.println("WebSocket failure! "), Throwable::printStackTrace));

            rxWebSocket.connect();

            /*
            messageDisposable = Observable.interval(1, 1, TimeUnit.SECONDS, Schedulers.io()).
                    doOnNext(aLong -> {
                        disposables.add(rxWebSocket.sendMessage("WebSocket Message!" + aLong)
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                                subscribe(success -> System.out.println("Message sent result: " + success), Throwable::printStackTrace));
                    })
                            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                    subscribe(success -> System.out.println("Interval " + success), Throwable::printStackTrace);
                    */
        }
        else{

            fragment.stopAnimation();

            if (rxWebSocket != null)
                disposables.add(rxWebSocket.close().subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(success -> System.out.println("WebSocket close initiated. With result: " + success),
                                Throwable::printStackTrace));
            else if (disposables != null)
                for (Disposable disposable : disposables)
                    disposable.dispose();
            else
                System.out.println("RxWebSocket == null");

            if (messageDisposable != null)
                messageDisposable.dispose();
        }
    }


    @Override
    public boolean mayStartHttpRequest() {
        return true;
    }

    @Override
    public void onResponse(PostModel postModel) {

    }

    @Override
    public void onFailure(String errorMessage) {

    }
}