package com.vitaly_kuznetsov.point.base_models.mvp_base_contract;

import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.PostModel;
import com.vitaly_kuznetsov.point.base_models.user_data_model.model.UserDataModel;

public interface BasicModelActionsInterface {

    UserDataModel getUserDataModel();
    void onResponse(PostModel postModel);
    void onFailure(String errorMessage);

}
