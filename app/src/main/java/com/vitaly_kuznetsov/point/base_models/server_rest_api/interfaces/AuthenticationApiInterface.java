package com.vitaly_kuznetsov.point.base_models.server_rest_api.interfaces;

import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BasicModelActionsInterface;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.PostModel;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.request_models.RequestModel;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.request_models.RequestPayload;

import retrofit2.Call;
import retrofit2.Callback;

interface AuthenticationApiInterface extends Callback<PostModel> {

    void start(BasicModelActionsInterface presenter);

    RequestPayload getPayload(BasicModelActionsInterface presenter);
    Call<PostModel> getApi(RequestModel requestModel);

}
