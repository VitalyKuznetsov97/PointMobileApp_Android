package com.vitaly_kuznetsov.point.base_models.server_rest_api.interfaces;

import android.support.annotation.NonNull;

import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BasicModelActionsInterface;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.PostModel;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.api.PointServiceApi;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.request_models.RequestModel;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.request_models.RequestPayload;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.retrofit_builders.BasicRetrofitBuilder;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public abstract class AbstractApiRequestController implements AuthenticationApiInterface {

    protected PointServiceApi api;
    private BasicModelActionsInterface presenter;

    /*----------------------------------------------------------
             A base class, that starts http requests

             Uses PointServiceApi
     ----------------------------------------------------------
     */

    @Override
    public void start(BasicModelActionsInterface presenter) {

        this.presenter = presenter;
        Retrofit retrofit = BasicRetrofitBuilder.getRetrofitInstance();
        api = retrofit.create(PointServiceApi.class);

        RequestPayload requestPayload = getPayload(presenter);

        RequestModel requestModel = new RequestModel(requestPayload);

        Call<PostModel> call = getApi(requestModel);

        call.enqueue(this);
    }

    @Override
    public void onResponse(@NonNull Call<PostModel> call, @NonNull Response<PostModel> response) {
        if (presenter != null)
            presenter.onResponse(response.body());
    }

    @Override
    public void onFailure(@NonNull Call<PostModel> call, @NonNull Throwable throwable) {
        if (presenter != null)
            presenter.onFailure(throwable.getMessage());
    }
}
