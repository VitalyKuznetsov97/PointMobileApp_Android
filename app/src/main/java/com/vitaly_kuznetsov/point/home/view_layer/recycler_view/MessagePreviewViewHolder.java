package com.vitaly_kuznetsov.point.home.view_layer.recycler_view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vitaly_kuznetsov.point.R;

class MessagePreviewViewHolder extends RecyclerView.ViewHolder {

    private View divider;

    MessagePreviewViewHolder(@NonNull View itemView) {
        super(itemView);
        divider = itemView.findViewById(R.id.divider_0);
    }

    void hideDivider(){
        divider.setAlpha(0);
    }
}