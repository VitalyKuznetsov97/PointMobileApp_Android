package com.vitaly_kuznetsov.point.settings.model_layer;

import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BasicModelActionsInterface;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.interfaces.AbstractApiRequestController;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.PostModel;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.request_models.RequestModel;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.request_models.RequestPayload;

import retrofit2.Call;

public class ProfileEditProfileRequestController extends AbstractApiRequestController {

    private static final ProfileEditProfileRequestController ourInstance = new ProfileEditProfileRequestController();

    public static ProfileEditProfileRequestController getInstance() {
        return ourInstance;
    }

    private ProfileEditProfileRequestController() {
    }

    @Override
    public RequestPayload getPayload(BasicModelActionsInterface presenter) {
        return RequestPayload.createProfileEditProfile(presenter.getUserDataModel());
    }

    @Override
    public Call<PostModel> getApi(RequestModel requestModel) {
        return api.profileEditProfile(requestModel);
    }
}