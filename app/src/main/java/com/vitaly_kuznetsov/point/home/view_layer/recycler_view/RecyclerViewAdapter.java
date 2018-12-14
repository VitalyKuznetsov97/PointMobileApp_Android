package com.vitaly_kuznetsov.point.home.view_layer.recycler_view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.home.model_layer.MessagePreview;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter {

    private ArrayList<MessagePreview> messagePreviewArrayList;

    public RecyclerViewAdapter(ArrayList<MessagePreview> messageArrayList) {
        this.messagePreviewArrayList = messageArrayList;
    }

    @Override
    public int getItemCount() {
        return messagePreviewArrayList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_preview, parent, false);

        return new MessagePreviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position == getItemCount() - 1){
            MessagePreviewViewHolder messagePreviewViewHolder = (MessagePreviewViewHolder) holder;
            messagePreviewViewHolder.hideDivider();
        }
    }
}