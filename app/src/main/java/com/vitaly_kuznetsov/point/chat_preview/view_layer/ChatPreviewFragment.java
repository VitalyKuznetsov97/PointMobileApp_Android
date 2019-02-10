package com.vitaly_kuznetsov.point.chat_preview.view_layer;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BasicFragmentInterface;
import com.vitaly_kuznetsov.point.chat_preview.presenter_layer.ChatPreviewViewPresenter;
import com.vitaly_kuznetsov.point.home.view_layer.activities.HomeActivity;

public class ChatPreviewFragment extends Fragment implements BasicFragmentInterface, ChatPreviewFragmentInterface {

    private ChatPreviewViewPresenter currentPresenter;
    private HomeActivity homeActivity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (currentPresenter == null) throw new RuntimeException();
        else {
            int layout = currentPresenter.isEmpty() ? R.layout.fragment_chat_empty : R.layout.fragment_chat;
            View view = inflater.inflate(layout, container, false);
            init(view);
            return view;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        currentPresenter.detachView();
        currentPresenter = null;
    }

    @Override
    public void attachPresenter(HomeActivity homeActivity, ChatPreviewViewPresenter presenter) {
        this.homeActivity = homeActivity;
        currentPresenter = presenter;
        currentPresenter.attachView(this);
    }

    @Override
    public void init(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_messages);
        if (!currentPresenter.isEmpty()) currentPresenter.onFragmentCreated(recyclerView);
        else {
            TextView refresh = view.findViewById(R.id.text_description_2);
            refresh.setOnClickListener(view1 -> currentPresenter.onRefreshClicked());
            refresh.setPaintFlags(refresh.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }
    }

    @Override
    public boolean isReadyToProgress() {
        return true;
    }

    @Override
    public HomeActivity getHomeActivity() {
        return homeActivity;
    }
}
