package com.vitaly_kuznetsov.point.base_models.web_socket_api.rxwebsocket;

import android.util.Log;

import com.vitaly_kuznetsov.point.base_models.web_socket_api.rxwebsocket.entities.SocketEvent;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class RxWebSocketLogger implements Subscriber<SocketEvent> {

    private final String TAG;

    public RxWebSocketLogger(String tag) {
        TAG = tag + ": ";
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "Complete");
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "Error");
        Log.e(TAG, e.getMessage());
        e.printStackTrace();
    }

    @Override
    public void onSubscribe(Subscription s) {
        Log.e(TAG, "Subscribe");
    }

    @Override
    public void onNext(SocketEvent socketEvent) {
        Log.d(TAG, "Next");
        Log.d(TAG, socketEvent.toString());
    }
}
