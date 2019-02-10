package com.vitaly_kuznetsov.point.chat_preview.model_layer.http_requests;

import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BasicModelActionsInterface;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.interfaces.AbstractApiRequestController;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.PostModel;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.request_models.RequestModel;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.request_models.RequestPayload;

import retrofit2.Call;

public class HistoryGetRequestController extends AbstractApiRequestController {

    private static final HistoryGetRequestController ourInstance = new HistoryGetRequestController();

    public static HistoryGetRequestController getInstance() {
        return ourInstance;
    }

    private HistoryGetRequestController() {
    }

    @Override
    public RequestPayload getPayload(BasicModelActionsInterface presenter) {
        return RequestPayload.createHistoryGet(presenter.getUserDataModel());
    }

    @Override
    public Call<PostModel> getApi(RequestModel requestModel) {
        return api.historyGet(requestModel);
    }
}