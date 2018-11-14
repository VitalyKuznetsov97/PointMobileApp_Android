package com.vitaly_kuznetsov.point.authentication.model_layer.server.login;

import com.vitaly_kuznetsov.point.authentication.model_layer.server.interfaces.AbstractApiRequestController;
import com.vitaly_kuznetsov.point.authentication.presenter_layer.interfaces.AbstractAuthenticationPresenter;
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
    public RequestPayload getPayload(AbstractAuthenticationPresenter authenticationPresenter) {
        return RequestPayload.createLoginCheckToken(authenticationPresenter.getUserDataModel());
    }

    @Override
    public Call<PostModel> getApi(RequestModel requestModel) {
        return api.loginCheckToken(requestModel);
    }
}