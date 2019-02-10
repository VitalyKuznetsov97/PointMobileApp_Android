package com.vitaly_kuznetsov.point.chat_preview.model_layer.recycler_view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vitaly_kuznetsov.point.chat_preview.presenter_layer.ChatPreviewViewPresenter;

import java.util.ArrayList;

public class MessagePreviewController {

    private RecyclerView recyclerView;
    private ChatPreviewViewPresenter previewViewPresenter;

    public MessagePreviewController(RecyclerView recyclerView, Context context, ChatPreviewViewPresenter presenter){
        recyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        this.recyclerView = recyclerView;
        this.previewViewPresenter = presenter;
    }

    public void setAdapter(ArrayList<MessagePreviewViewHolderModel> arrayList){
        MessagePreviewRecyclerViewAdapter adapter = new MessagePreviewRecyclerViewAdapter(arrayList, recyclerView, this);
        recyclerView.setAdapter(adapter);
    }

    void onItemClicked(int position){
        previewViewPresenter.onChatClicked(position);
    }

}
