package com.vitaly_kuznetsov.point.chat.model_layer.recycler_view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vitaly_kuznetsov.point.R;

import java.util.ArrayList;

public class MessagesRecyclerViewAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private ArrayList<MessageViewHolderModel> messageViewHolderModelArrayList;

    private boolean onBottom;
    boolean isOnBottom() { return onBottom; }

    MessagesRecyclerViewAdapter(ArrayList<MessageViewHolderModel> messageViewHolderModelArrayList) {
        this.messageViewHolderModelArrayList = messageViewHolderModelArrayList;
    }

    void addMessage(MessageViewHolderModel messageViewHolderModel){
        messageViewHolderModelArrayList.add(messageViewHolderModel);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return messageViewHolderModelArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        MessageViewHolderModel messageViewHolderModel = messageViewHolderModelArrayList.get(position);

        return  messageViewHolderModel.isFromMe() ? VIEW_TYPE_MESSAGE_SENT : VIEW_TYPE_MESSAGE_RECEIVED;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(viewType == VIEW_TYPE_MESSAGE_SENT ?
                R.layout.my_message : R.layout.friend_message, parent, false);

        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessageViewHolderModel messageViewHolderModel = messageViewHolderModelArrayList.get(position);
        ((MessageViewHolder) holder).bind(messageViewHolderModel);
        onBottom = position == messageViewHolderModelArrayList.size() - 1;
    }
}