package com.vitaly_kuznetsov.point.home.model_layer;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vitaly_kuznetsov.point.home.view_layer.recycler_view.RecyclerViewAdapter;

import java.util.ArrayList;

public class MessagePreviewController {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

    public MessagePreviewController(RecyclerView recyclerView, Context context){
        this.recyclerView = recyclerView;

        this.recyclerView.setHasFixedSize(false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void setAdapter(){
        ArrayList<MessagePreview> messagePreviewArrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            messagePreviewArrayList.add(new MessagePreview());

        adapter = new RecyclerViewAdapter(messagePreviewArrayList);
        recyclerView.setAdapter(adapter);
    }

}
