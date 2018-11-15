package com.vitaly_kuznetsov.point.authentication.model_layer.server.interfaces;

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
              A base class, that starts http requests from
             /server/login and /server/registration

             Uses PointServiceApi
     ----------------------------------------------------------
     */

    @Override
    public void start(BasicModelActionsInterface authenticationPresenter) {

        //TODO find out, how to create a viable progressBar

        presenter =  authenticationPresenter;
        Retrofit retrofit = BasicRetrofitBuilder.getRetrofitInstance();
        api = retrofit.create(PointServiceApi.class);

        RequestPayload requestPayload = getPayload(authenticationPresenter);

        RequestModel requestModel = new RequestModel(requestPayload);

        Call<PostModel> call = getApi(requestModel);

        call.enqueue(this);
    }

    @Override
    public void onResponse(@NonNull Call<PostModel> call, @NonNull Response<PostModel> response) {
        presenter.onResponse(response.body());
    }

    @Override
    public void onFailure(@NonNull Call<PostModel> call, @NonNull Throwable t) {
        presenter.onFailure(t.getMessage());
    }
}
