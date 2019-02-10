package com.vitaly_kuznetsov.point.chat.view_layer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BaseContract;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.Chatmade;
import com.vitaly_kuznetsov.point.chat.presenter_layer.ChatViewPresenter;
import com.vitaly_kuznetsov.point.home.view_layer.activities.HomeActivity;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.Unregistrar;

public class ChatActivity extends AppCompatActivity implements BaseContract.View, ChatViewInterface {

    private EditText messageEditText;
    private Unregistrar unregistrar;
    private RecyclerView recyclerView;

    private ChatViewPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean shouldStart = getIntent().getBooleanExtra("Should Start", false);

        if (shouldStart)
            setContentView(R.layout.activity_chat);
        else
            setContentView(R.layout.activity_chat_empty);

        presenter = new ChatViewPresenter(getIntent().getStringExtra("Chat"), shouldStart);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unregistrar!=null) unregistrar.unregister();
        if (presenter!=null) {
            presenter.detachView();
            presenter = null;
        }
    }

    @Override
    public void init() {
        ImageView goBackImageView = findViewById(R.id.go_back);
        ImageView sendMessageImageView = findViewById(R.id.send_button);
        ImageView emojiImageView = findViewById(R.id.smile_image);
        messageEditText = findViewById(R.id.message_edit_text);
        recyclerView = findViewById(R.id.recycle_view_messages);

        presenter.attachView(this);
        goBackImageView.setOnClickListener(view -> presenter.onBackIconClicked());

        if (messageEditText != null && sendMessageImageView != null && emojiImageView != null) {

            sendMessageImageView.setOnClickListener(view -> {
                String message = String.valueOf(messageEditText.getText());
                if (!message.equals("")) {
                    messageEditText.setText("");
                    presenter.onSendMessageClicked(message);
                }
            });

            emojiImageView.setOnClickListener(view -> {
            });

            unregistrar= KeyboardVisibilityEvent.registerEventListener(this,
                    isOpen -> {if(isOpen) presenter.onKeyBoardUp();});
        }
    }

    @Override
    public void goToHomeActivity(String errorMessage) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("Error", errorMessage);
        startActivity(intent);
        finish();
    }

    @Override
    public void goToHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public BaseContract.Presenter getPresenter() {
        return presenter;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public void setChatInterior(Chatmade chatmade) {
        TextView textView = findViewById(R.id.top_text);
        textView.setText(chatmade.getNick());
    }

}
