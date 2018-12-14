package com.vitaly_kuznetsov.point.base_models.mvp_base_contract;

import android.view.View;

public interface BasicFragmentInterface {
    void init(View view);
    boolean isReadyToProgress();
}