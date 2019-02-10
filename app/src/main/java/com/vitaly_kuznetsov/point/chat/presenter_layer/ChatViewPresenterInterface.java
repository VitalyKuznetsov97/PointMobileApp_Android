package com.vitaly_kuznetsov.point.chat.presenter_layer;

import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.Message;

public interface ChatViewPresenterInterface {

    void showMessages();
    void startChat();

    void onMessageReceived(Message message);
    void onError(String errorMessage);
    void onClose();

    void onKeyBoardUp();
    void onBackIconClicked();
    void onSendMessageClicked(String message);
}
