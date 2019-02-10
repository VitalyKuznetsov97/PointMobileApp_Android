package com.vitaly_kuznetsov.point.search.presenter_layer;

import com.vitaly_kuznetsov.point.base_models.user_data_model.model.UserDataModel;
import com.vitaly_kuznetsov.point.base_models.web_socket_api.response_models.User;
import com.vitaly_kuznetsov.point.home.presenter_layer.HomeViewPresenter;
import com.vitaly_kuznetsov.point.search.model_layer.SearchWebSocketController;
import com.vitaly_kuznetsov.point.search.view_layer.MatchFragment;

public class MatchFragmentPresenter implements MatchFragmentPresenterInterface,
                WebSocketHandlingInterface{

    private UserDataModel matchedUserDataModel;
    private MatchFragment fragment;
    private SearchWebSocketController webSocketController;

    private boolean closeSocket;

    public MatchFragmentPresenter(User matchedUser, SearchWebSocketController webSocketController) {
        this.matchedUserDataModel = matchedUser.createUserDataModel();
        this.webSocketController = webSocketController;
        this.webSocketController.setPresenter(this);
    }

    @Override
    public void attachView(MatchFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void detachView() {
        fragment = null;
        webSocketController = null;
    }

    public UserDataModel getUserDataModel() {
        return matchedUserDataModel;
    }

    @Override
    public void onButtonAfterAnimationClicked() {
        fragment.getHomeActivity().showToastMessage("Waiting for your Match to accept the offer! ;)");
    }

    @Override
    public void onAcceptButtonClicked() {
        webSocketController.sendMessage("true");
        fragment.startButtonAnimation();
    }

    @Override
    public void onDeclineButtonClicked() {
        closeSocket = false;
        webSocketController.sendMessage("false");
    }

    @Override
    public void onCancelButtonClicked() {
        closeSocket = true;
        if(webSocketController != null) webSocketController.sendMessage("false");
    }

    @Override
    public void onWebSocketResponse(String response) {
        if (response != null && response.contains("chatId"))
            fragment.getHomeActivity().goToChatActivity(response, true);
        else if (response != null && response.contains("false"))
            ((HomeViewPresenter) fragment.getHomeActivity().getPresenter()).onMatchDeclined(webSocketController);
    }

    @Override
    public void onMessageSent(String message) {
        if (message.equals("false")){
            if (fragment != null && fragment.getHomeActivity() != null) {
                if (!closeSocket)
                    ((HomeViewPresenter) fragment.getHomeActivity().getPresenter()).declineMatch(webSocketController);
                else
                    ((HomeViewPresenter) fragment.getHomeActivity().getPresenter()).cancelSearch(webSocketController);
            }
            else if (webSocketController != null) webSocketController.stop();
        }
    }

    @Override
    public void onError(String message) {
        webSocketController.stop();
        ((HomeViewPresenter) fragment.getHomeActivity().getPresenter()).onWebSocketError(webSocketController, message);
    }
}
