package com.vitaly_kuznetsov.point.authentication.model_layer.server.login;

import com.vitaly_kuznetsov.point.authentication.model_layer.server.interfaces.AbstractApiRequestController;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BasicModelActionsInterface;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.PostModel;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.request_models.RequestModel;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.request_models.RequestPayload;

import retrofit2.Call;

public class LoginCheckPhoneRequestController extends AbstractApiRequestController {

    private static final LoginCheckPhoneRequestController ourInstance = new LoginCheckPhoneRequestController();

    public static LoginCheckPhoneRequestController getInstance() {
        return ourInstance;
    }

    private LoginCheckPhoneRequestController() {
    }

    @Override
    public RequestPayload getPayload(BasicModelActionsInterface authenticationPresenter) {
        return RequestPayload.createCheckPhonePayload(authenticationPresenter.getUserDataModel());
    }

    @Override
    public Call<PostModel> getApi(RequestModel requestModel) {
        return api.loginCheckPhone(requestModel);
    }
}