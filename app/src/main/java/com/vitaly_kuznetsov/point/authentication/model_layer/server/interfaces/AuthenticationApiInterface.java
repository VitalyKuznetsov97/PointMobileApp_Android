package com.vitaly_kuznetsov.point.authentication.model_layer.server.interfaces;

import com.vitaly_kuznetsov.point.authentication.presenter_layer.interfaces.AbstractAuthenticationPresenter;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BasicModelActionsInterface;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.PostModel;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.request_models.RequestModel;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.request_models.RequestPayload;

import retrofit2.Call;
import retrofit2.Callback;

interface AuthenticationApiInterface extends Callback<PostModel> {

    void start(BasicModelActionsInterface authenticationPresenter);

    RequestPayload getPayload(BasicModelActionsInterface authenticationPresenter);
    Call<PostModel> getApi(RequestModel requestModel);

}
