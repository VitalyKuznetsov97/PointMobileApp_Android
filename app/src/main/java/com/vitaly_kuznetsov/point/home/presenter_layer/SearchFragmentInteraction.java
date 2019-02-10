package com.vitaly_kuznetsov.point.home.presenter_layer;

import com.vitaly_kuznetsov.point.base_models.web_socket_api.response_models.User;
import com.vitaly_kuznetsov.point.search.model_layer.SearchWebSocketController;

public interface SearchFragmentInteraction {

    void onUserMatched(User user, SearchWebSocketController searchWebSocketController);
    void onMatchDeclined(SearchWebSocketController webSocketController);
    void declineMatch(SearchWebSocketController webSocketController);
    void cancelSearch(SearchWebSocketController webSocketController);
    void onWebSocketError(SearchWebSocketController webSocketController, String errorMessage);

}
