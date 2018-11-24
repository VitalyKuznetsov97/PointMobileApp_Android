package com.vitaly_kuznetsov.point.launch_app.view_layer;

import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BaseContract;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.PostModel;

public interface LaunchViewInterface extends BaseContract.View {

    void startTimer();
    void stopTimer();
    void goToMainActivity();
    void goToHomeActivity(PostModel postModel);
    void goToHomeActivity(String errorText);
    void goToHomeActivityOnFailure(String errorText);

}
