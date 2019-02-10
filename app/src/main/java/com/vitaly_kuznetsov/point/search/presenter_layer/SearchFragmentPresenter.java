package com.vitaly_kuznetsov.point.search.presenter_layer;

import com.google.gson.Gson;
import com.vitaly_kuznetsov.point.base_models.user_data_model.model.UserDataModel;
import com.vitaly_kuznetsov.point.base_models.user_data_model.user_model_preferences.ModelHandler;
import com.vitaly_kuznetsov.point.base_models.web_socket_api.response_models.User;
import com.vitaly_kuznetsov.point.home.presenter_layer.HomeViewPresenter;
import com.vitaly_kuznetsov.point.search.model_layer.SearchWebSocketController;
import com.vitaly_kuznetsov.point.search.view_layer.MatchFragment;
import com.vitaly_kuznetsov.point.search.view_layer.SearchFragment;

import java.util.HashMap;

public class SearchFragmentPresenter implements
                    SearchFragmentPresenterInterface, WebSocketHandlingInterface{

    private SearchFragment fragment;
    private UserDataModel userDataModel;
    private SearchWebSocketController webSocketController;

    public SearchFragmentPresenter(SearchWebSocketController webSocketController) {
        this.webSocketController = webSocketController;
        webSocketController.setPresenter(this);
    }

    public SearchFragmentPresenter() { }

    @Override
    public void attachView(SearchFragment fragment) {
        this.fragment = fragment;
        userDataModel = ModelHandler.getInstance(fragment.getContext());

        if (webSocketController == null)
            webSocketController = new SearchWebSocketController(this);
    }

    @Override
    public void detachView() {
        if (!(fragment.getHomeActivity().getCurrentFragment() instanceof MatchFragment) &&
                webSocketController.isStarted())
            webSocketController.stop();

        fragment = null;
        webSocketController = null;
        userDataModel = null;
    }

    @Override
    public void onFragmentResumed() {
        if (webSocketController.isStarted() && !fragment.isPointMainButtonChecked())
            fragment.setPointMainButtonChecked(true);
        else if (fragment.isPointMainButtonChecked()){
            fragment.stopAnimation();
            fragment.startAnimation();
        }
    }

    @Override
    public void onMainButtonCheckedChanged(boolean checked) {

        if (checked) {
            fragment.startAnimation();
            if (!webSocketController.isStarted()) {
                HashMap<String, String> socketParams = new HashMap<>();
                socketParams.put("token", userDataModel.getToken());
                socketParams.put("longitude", "37.744533");
                socketParams.put("latitude", "55.622137");
                webSocketController.start(socketParams);
            }
        } else {
            fragment.stopAnimation();
            webSocketController.stop();
        }

    }

    @Override
    public void onWebSocketResponse(String response) {
        Gson gson = new Gson();
        User user = gson.fromJson(response, User.class);
        HomeViewPresenter homeViewPresenter = (HomeViewPresenter) fragment.getHomeActivity().getPresenter();
        homeViewPresenter.onUserMatched(user, webSocketController);
    }

    @Override
    public void onMessageSent(String message) { }

    @Override
    public void onError(String message) {
        //((HomeViewPresenter) fragment.getHomeActivity().getPresenter()).onWebSocketError(message);

        fragment.setPointMainButtonChecked(false);
        if(webSocketController.isStarted()) webSocketController.stop();
    }
}