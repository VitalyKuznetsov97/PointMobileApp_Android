package com.vitaly_kuznetsov.point.chat_preview.model_layer.recycler_view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vitaly_kuznetsov.point.R;

import java.util.ArrayList;

public class MessagePreviewRecyclerViewAdapter extends RecyclerView.Adapter {

    private ArrayList<MessagePreviewViewHolderModel> messagePreviewViewHolderModelArrayList;
    private View.OnClickListener onClickListener;

    MessagePreviewRecyclerViewAdapter(ArrayList<MessagePreviewViewHolderModel> messageArrayList,
                             RecyclerView recyclerView, MessagePreviewController controller) {
        this.messagePreviewViewHolderModelArrayList = messageArrayList;
        onClickListener = view -> {
            int position =  recyclerView.getChildAdapterPosition(view);
            controller.onItemClicked(position);
        };
    }

    @Override
    public int getItemCount() {
        return messagePreviewViewHolderModelArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);
        if (position==0) return R.layout.message_preview_empty_space;
        else return R.layout.message_preview;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        if (viewType == R.layout.message_preview) view.setOnClickListener(onClickListener);

        return new MessagePreviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessagePreviewViewHolderModel messagePreviewViewHolderModel = messagePreviewViewHolderModelArrayList.get(position);

        ((MessagePreviewViewHolder) holder).bind(messagePreviewViewHolderModel);
    }
}