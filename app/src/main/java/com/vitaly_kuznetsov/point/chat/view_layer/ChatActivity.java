package com.vitaly_kuznetsov.point.chat.view_layer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BaseContract;
import com.vitaly_kuznetsov.point.chat.presenter_layer.MessagesRecyclerViewController;

import java.util.Date;

public class ChatActivity extends AppCompatActivity implements BaseContract.View {

    private ImageView goBackImageView;
    private ImageView sendMessageImageView;
    private ImageView emojiImageView;
    private EditText messageEditText;
    MessagesRecyclerViewController recyclerViewController;

    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        init();
    }

    @Override
    public void init() {
        goBackImageView = findViewById(R.id.go_back);
        sendMessageImageView = findViewById(R.id.send_button);
        emojiImageView = findViewById(R.id.smile_image);
        messageEditText = findViewById(R.id.message_edit_text);

        RecyclerView messageRecyclerView = findViewById(R.id.recycle_view_messages);
        recyclerViewController = new MessagesRecyclerViewController(messageRecyclerView, this);
        recyclerViewController.setAdapter();

        goBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        sendMessageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = String.valueOf(messageEditText.getText());
                messageEditText.setText("");
                recyclerViewController.sendMessage(flag, message, new Date());
                flag = !flag;
            }
        });

        emojiImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }

    @Override
    public BaseContract.Presenter getPresenter() {
        return null;
    }
}
