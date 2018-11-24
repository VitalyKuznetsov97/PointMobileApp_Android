package com.vitaly_kuznetsov.point.launch_app.model_layer;

import com.vitaly_kuznetsov.point.base_models.server_rest_api.interfaces.AbstractApiRequestController;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BasicModelActionsInterface;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.PostModel;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.request_models.RequestModel;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.request_models.RequestPayload;

import retrofit2.Call;

public class LoginCheckTokenRequestController extends AbstractApiRequestController {

    private static final LoginCheckTokenRequestController ourInstance = new LoginCheckTokenRequestController();

    public static LoginCheckTokenRequestController getInstance() {
        return ourInstance;
    }

    private LoginCheckTokenRequestController() {
    }

    @Override
    public RequestPayload getPayload(BasicModelActionsInterface presenter) {
        return RequestPayload.createLoginCheckToken(presenter.getUserDataModel());
    }

    @Override
    public Call<PostModel> getApi(RequestModel requestModel) {
        return api.loginCheckToken(requestModel);
    }
}