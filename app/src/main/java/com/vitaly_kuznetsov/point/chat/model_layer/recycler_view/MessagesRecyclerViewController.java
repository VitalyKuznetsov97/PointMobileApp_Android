package com.vitaly_kuznetsov.point.chat.model_layer.recycler_view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MessagesRecyclerViewController {

    private MessagesRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    public MessagesRecyclerViewController(RecyclerView recyclerView, Context context){
        recyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        this.recyclerView = recyclerView;
    }

    public void setAdapter(ArrayList<MessageViewHolderModel> arrayList){
        adapter = new MessagesRecyclerViewAdapter(arrayList);
        recyclerView.setAdapter(adapter);
        if (arrayList != null && arrayList.size() != 0) scrollRecyclerView();
    }

    public void setAdapter(){ setAdapter(new ArrayList<>()); }

    private void scrollRecyclerView(){
        recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
    }

    public void postMessage(MessageViewHolderModel messageViewHolderModel){
        adapter.addMessage(messageViewHolderModel);
        if (adapter.isOnBottom()) scrollRecyclerView();
    }

    public void keyBoardOpened(){
        scrollRecyclerView();
    }

}
