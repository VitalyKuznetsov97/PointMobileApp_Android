package com.vitaly_kuznetsov.point.authentication.model_layer.server.registration;

import com.vitaly_kuznetsov.point.authentication.model_layer.server.interfaces.AbstractApiRequestController;
import com.vitaly_kuznetsov.point.authentication.presenter_layer.interfaces.AbstractAuthenticationPresenter;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.PostModel;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.request_models.RequestModel;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.request_models.RequestPayload;

import retrofit2.Call;

public class RegistrationCheckPhoneRequestController extends AbstractApiRequestController {

    private static final RegistrationCheckPhoneRequestController ourInstance = new RegistrationCheckPhoneRequestController();

    public static RegistrationCheckPhoneRequestController getInstance() {
        return ourInstance;
    }

    private RegistrationCheckPhoneRequestController() {
    }

    @Override
    public RequestPayload getPayload(AbstractAuthenticationPresenter authenticationPresenter) {
        return RequestPayload.createCheckPhonePayload(authenticationPresenter.getUserDataModel());
    }

    @Override
    public Call<PostModel> getApi(RequestModel requestModel) {
        return api.registrationCheckPhone(requestModel);
    }
}