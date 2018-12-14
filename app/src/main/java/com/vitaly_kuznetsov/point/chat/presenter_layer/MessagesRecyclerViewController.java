package com.vitaly_kuznetsov.point.chat.presenter_layer;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vitaly_kuznetsov.point.chat.model_layer.Message;
import com.vitaly_kuznetsov.point.chat.model_layer.MessagesRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Date;

public class MessagesRecyclerViewController {

    private RecyclerView recyclerView;
    private MessagesRecyclerViewAdapter adapter;

    public MessagesRecyclerViewController(RecyclerView recyclerView, Context context){
        this.recyclerView = recyclerView;

        this.recyclerView.setHasFixedSize(false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void setAdapter(){
        adapter = new MessagesRecyclerViewAdapter(new ArrayList<Message>());
        recyclerView.setAdapter(adapter);
    }

    public void sendMessage(boolean isFromMe, String messageText, Date date){
        Message message = new Message(messageText, date, isFromMe);
        adapter.addMessage(message);
    }

}
