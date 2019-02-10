package com.vitaly_kuznetsov.point.chat.model_layer.recycler_view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.vitaly_kuznetsov.point.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class MessageViewHolder extends RecyclerView.ViewHolder {

    private TextView messageText, timeStamp;

    MessageViewHolder(@NonNull View itemView) {
        super(itemView);
        messageText = itemView.findViewById(R.id.message_body);
        timeStamp = itemView.findViewById(R.id.message_time);
    }

    void bind(MessageViewHolderModel messageViewHolderModel) {
        messageText.setText(messageViewHolderModel.getMessage());

        // Format the stored timestamp into a readable String using method.
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());
        Date date = new Date();
        date.setTime(messageViewHolderModel.getTime());
        timeStamp.setText(dateFormat.format(date));
    }


}
