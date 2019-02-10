package com.vitaly_kuznetsov.point.home.presenter_layer;

import android.content.Context;

import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BaseContract;
import com.vitaly_kuznetsov.point.base_models.user_data_model.model.UserDataModel;
import com.vitaly_kuznetsov.point.base_models.user_data_model.user_model_preferences.ModelHandler;
import com.vitaly_kuznetsov.point.base_models.web_socket_api.response_models.User;
import com.vitaly_kuznetsov.point.home.view_layer.activities.HomeActivity;
import com.vitaly_kuznetsov.point.home.view_layer.interfaces.BasicUiActionsHome;
import com.vitaly_kuznetsov.point.search.model_layer.SearchWebSocketController;
import com.vitaly_kuznetsov.point.search.presenter_layer.MatchFragmentPresenter;
import com.vitaly_kuznetsov.point.search.presenter_layer.SearchFragmentPresenter;
import com.vitaly_kuznetsov.point.search.view_layer.MatchFragment;
import com.vitaly_kuznetsov.point.search.view_layer.SearchFragment;

import static com.vitaly_kuznetsov.point.base_models.reusable_fragments.CustomDialog.ERROR_ALERT;

public class HomeViewPresenter implements BaseContract.Presenter, HomeViewPresenterInterface, SearchFragmentInteraction {

    private BasicUiActionsHome currentView;

    private static final String TOAST_MESSAGE_ON_MATCH_DECLINE =
            "Ooops! Your Match has declined the offer!\n Don't worry, you will find another one. ;)";

    public UserDataModel getUserDataModel(){
        return ModelHandler.getInstance((Context) currentView);
    }
    @Override
    public void attachView(BaseContract.View view) {
        currentView = (BasicUiActionsHome) view;
    }

    @Override
    public void detachView() {
        currentView = null;
    }

    //---------------------------Search Fragment Interaction--------------------------------------
    @Override
    public void onUserMatched(User user, SearchWebSocketController searchWebSocketController) {

        MatchFragmentPresenter presenter = new MatchFragmentPresenter(user, searchWebSocketController);
        MatchFragment matchFragment = new MatchFragment();
        matchFragment.attachPresenter(presenter);

        ((HomeActivity) currentView).showFrag(matchFragment);
    }

    private void showSearchFragment(SearchWebSocketController searchWebSocketController){

        SearchFragmentPresenter presenter = new SearchFragmentPresenter(searchWebSocketController);
        SearchFragment searchFragment = new SearchFragment();
        searchFragment.attachPresenter(presenter);

        ((HomeActivity) currentView).showFrag(searchFragment);
    }

    //---------------------------Match Fragment Interaction--------------------------------------

    @Override
    public void onMatchDeclined(SearchWebSocketController webSocketController) {
        showSearchFragment(webSocketController);
        ((HomeActivity) currentView).showToastMessage(TOAST_MESSAGE_ON_MATCH_DECLINE);
    }

    @Override
    public void declineMatch(SearchWebSocketController webSocketController) {
        showSearchFragment(webSocketController);
    }

    @Override
    public void cancelSearch(SearchWebSocketController webSocketController) {
        if (webSocketController.isStarted()) webSocketController.stop();
        showSearchFragment(webSocketController);
    }

    @Override
    public void onWebSocketError(SearchWebSocketController webSocketController, String errorMessage) {
        if (webSocketController.isStarted()) webSocketController.stop();
        showSearchFragment(webSocketController);
        currentView.showAlertDialog(ERROR_ALERT, errorMessage);
    }

    //---------------------------------------------------------------------------------------

}
