package com.vitaly_kuznetsov.point.chat_preview.model_layer.recycler_view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vitaly_kuznetsov.point.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class MessagePreviewViewHolder extends RecyclerView.ViewHolder {

    private TextView nickName, messageText, timeStamp;
    private View divider;
    private ImageView picture;

    MessagePreviewViewHolder(@NonNull View itemView) {
        super(itemView);
        nickName = itemView.findViewById(R.id.text_name);
        messageText = itemView.findViewById(R.id.message_body);
        timeStamp = itemView.findViewById(R.id.message_time);
        picture = itemView.findViewById(R.id.constraint_layout_picture);
        divider = itemView.findViewById(R.id.divider_0);
    }

    void bind(MessagePreviewViewHolderModel messagePreviewViewHolderModel) {
        if (nickName != null && messageText != null && timeStamp != null) {
            nickName.setText(messagePreviewViewHolderModel.getNick());
            messageText.setText(messagePreviewViewHolderModel.getMessage());

            // Format the stored timestamp into a readable String using method.
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());
            Date date = new Date();
            date.setTime(messagePreviewViewHolderModel.getTime());
            timeStamp.setText(dateFormat.format(date));
            if (messagePreviewViewHolderModel.isLast()) hideDivider();
        }
    }

    void hideDivider(){
        divider.setAlpha(0);
    }

}