package com.vitaly_kuznetsov.point.authentication.presenter_layer.interfaces;

import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.PostModel;
import com.vitaly_kuznetsov.point.base_models.user_data_model.UserDataModel;

interface BasicAuthenticationModelActions {

    UserDataModel getUserDataModel();
    void onResponse(PostModel postModel);
    void onFailure(String errorMessage);

}
