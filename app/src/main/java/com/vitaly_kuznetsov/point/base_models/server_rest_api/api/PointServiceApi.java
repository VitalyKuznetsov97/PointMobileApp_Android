package com.vitaly_kuznetsov.point.base_models.server_rest_api.api;

import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.PostModel;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.request_models.RequestModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PointServiceApi {

    //--------------Authentication---------------

    @POST("/registration/checkPhone")
    Call<PostModel> registrationCheckPhone(@Body RequestModel requestModel);

    @POST("/registration/createAccount")
    Call<PostModel> registrationCreateAccount(@Body RequestModel requestModel);

    @POST("/login/checkPhone")
    Call<PostModel> loginCheckPhone(@Body RequestModel requestModel);

    @POST("/login/submitSMS")
    Call<PostModel> loginSubmitSms(@Body RequestModel requestModel);

    @POST("/login/checkToken")
    Call<PostModel> loginCheckToken(@Body RequestModel requestModel);

    //---------------Home------------------

    @POST("/history/get")
    Call<PostModel> historyGet(@Body RequestModel requestModel);

    //---------------Settings------------------

    @POST("/profile/edit")
    Call<PostModel> profileEditProfile(@Body RequestModel requestModel);
}
