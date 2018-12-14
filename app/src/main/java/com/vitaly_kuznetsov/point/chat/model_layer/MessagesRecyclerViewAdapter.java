package com.vitaly_kuznetsov.point.chat.model_layer;

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

    private ArrayList<Message> messageArrayList;

    public MessagesRecyclerViewAdapter(ArrayList<Message> messageArrayList) {
        this.messageArrayList = messageArrayList;
    }

    public void addMessage(Message message){
        messageArrayList.add(message);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messageArrayList.get(position);
        if (message.isFromMe())
            return VIEW_TYPE_MESSAGE_SENT;
        else return VIEW_TYPE_MESSAGE_RECEIVED;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_message, parent, false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_message, parent, false);

        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Message message = messageArrayList.get(position);
        ((MessageViewHolder) holder).bind(message);
    }
}