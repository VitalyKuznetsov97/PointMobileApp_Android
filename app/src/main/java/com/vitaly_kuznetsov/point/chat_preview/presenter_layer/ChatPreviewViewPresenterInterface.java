package com.vitaly_kuznetsov.point.chat_preview.presenter_layer;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

public interface ChatPreviewViewPresenterInterface {

    void onChatClicked(int chatPosition);

    void onFragmentCreated(RecyclerView recyclerView);
    void onRefreshClicked();

    void attachView(Fragment fragment);
    void detachView();

    boolean isEmpty();
}
