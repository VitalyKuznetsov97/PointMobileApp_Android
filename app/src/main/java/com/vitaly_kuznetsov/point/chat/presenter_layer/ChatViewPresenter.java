package com.vitaly_kuznetsov.point.chat.presenter_layer;

import android.content.Context;

import com.google.gson.Gson;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BaseContract;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.Chat;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.Message;
import com.vitaly_kuznetsov.point.base_models.user_data_model.model.UserDataModel;
import com.vitaly_kuznetsov.point.base_models.user_data_model.user_model_preferences.ModelHandler;
import com.vitaly_kuznetsov.point.chat.model_layer.recycler_view.MessageViewHolderModel;
import com.vitaly_kuznetsov.point.chat.model_layer.recycler_view.MessagesRecyclerViewController;
import com.vitaly_kuznetsov.point.chat.model_layer.web_socket.ChatWebSocketController;
import com.vitaly_kuznetsov.point.chat.view_layer.ChatActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ChatViewPresenter implements BaseContract.Presenter, ChatViewPresenterInterface {

    private ChatActivity currentView;

    private MessagesRecyclerViewController recyclerViewController;

    private Chat chat;
    private ChatWebSocketController webSocketController;
    private boolean shouldStart;

    public ChatViewPresenter(String chat, boolean shouldStart) {
        Gson gson = new Gson();
        this.chat = gson.fromJson(chat, Chat.class);
        this.shouldStart = shouldStart;
    }

    @Override
    public void attachView(BaseContract.View view) {
        currentView = (ChatActivity) view;
        recyclerViewController = new MessagesRecyclerViewController(currentView.getRecyclerView(), (Context) view);

        currentView.setChatInterior(chat.getChatmade());
        showMessages();
        if (shouldStart) startChat();
    }

    @Override
    public void detachView() {
        currentView = null;
        recyclerViewController = null;
        if (webSocketController != null && webSocketController.isStarted()) webSocketController.stop();
        webSocketController = null;
    }

    @Override
    public void showMessages() {
        ArrayList<Message> messages = chat.getMessages();
        if (messages != null && messages.size() != 0){
            ArrayList<MessageViewHolderModel> messageViewHolderModelArrayList = new ArrayList<>();
            String id = chat.getChatmade().getId();
            for (Message message : messages){
                boolean isFromMe = true;
                if (message.getSenderId().equals(id)) isFromMe = false;
                messageViewHolderModelArrayList.
                        add(new MessageViewHolderModel
                                (message.getText(), Long.parseLong(message.getDate()), isFromMe));
            }
            recyclerViewController.setAdapter(messageViewHolderModelArrayList);
        }
        else {
            recyclerViewController.setAdapter();
        }
    }

    @Override
    public void onKeyBoardUp() {
        recyclerViewController.keyBoardOpened();
    }

    @Override
    public void startChat() {
        webSocketController = new ChatWebSocketController(this);

        UserDataModel userDataModel = ModelHandler.getInstance(currentView);
        HashMap<String, String> socketParams = new HashMap<>();
        socketParams.put("token", userDataModel.getToken());
        socketParams.put("yourID", chat.getChatmade().getId());
        webSocketController.start(socketParams);
    }

    @Override
    public void onBackIconClicked() {
        if (webSocketController != null && webSocketController.isStarted()) webSocketController.stop();
        else currentView.goToHomeActivity();
    }

    @Override
    public void onSendMessageClicked(String message) {
        Date date = new Date();
        MessageViewHolderModel messageViewHolderModel
                = new MessageViewHolderModel(message, date.getTime(), true);
        System.out.println(date.getTime());
        recyclerViewController.postMessage(messageViewHolderModel);
        webSocketController.sendMessage(message);
    }

    @Override
    public void onMessageReceived(Message message) {
        MessageViewHolderModel messageViewHolderModel
                = new MessageViewHolderModel(message.getText(), Long.parseLong(message.getDate()), false);
        System.out.println(message.getDate());
        recyclerViewController.postMessage(messageViewHolderModel);
    }

    @Override
    public void onClose() {
        currentView.goToHomeActivity();
    }

    @Override
    public void onError(String errorMessage) {
        currentView.goToHomeActivity(errorMessage);
    }

}
