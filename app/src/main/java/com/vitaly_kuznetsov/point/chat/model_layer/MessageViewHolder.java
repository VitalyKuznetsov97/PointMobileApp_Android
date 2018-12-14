package com.vitaly_kuznetsov.point.chat.model_layer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.vitaly_kuznetsov.point.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

class MessageViewHolder extends RecyclerView.ViewHolder {

    private TextView messageText, timeStamp;

    MessageViewHolder(@NonNull View itemView) {
        super(itemView);
        messageText = itemView.findViewById(R.id.message_body);
        timeStamp = itemView.findViewById(R.id.message_time);
    }

    void bind(Message message) {
        messageText.setText(message.getMessage());

        // Format the stored timestamp into a readable String using method.
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());
        timeStamp.setText(dateFormat.format(message.getTime()));
    }


}
