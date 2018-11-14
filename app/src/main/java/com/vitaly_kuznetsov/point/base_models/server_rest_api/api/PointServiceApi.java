package com.vitaly_kuznetsov.point.base_models.server_rest_api.api;

import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.PostModel;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.request_models.RequestModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PointServiceApi {

    //--------------Authentication---------------

    @POST("/registration/checkphone")
    Call<PostModel> registrationCheckPhone(@Body RequestModel requestModel);

    @POST("/registration/createaccount")
    Call<PostModel> registrationCreateAccount(@Body RequestModel requestModel);

    @POST("/login/checkphone")
    Call<PostModel> loginCheckPhone(@Body RequestModel requestModel);

    @POST("/login/submitsms")
    Call<PostModel> loginSubmitSms(@Body RequestModel requestModel);

    @POST("/login/checktoken")
    Call<PostModel> loginCheckToken(@Body RequestModel requestModel);

    //---------------Home------------------
}
