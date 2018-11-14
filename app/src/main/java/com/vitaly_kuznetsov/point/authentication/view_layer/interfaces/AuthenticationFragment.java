package com.vitaly_kuznetsov.point.authentication.view_layer.interfaces;

import android.view.View;

public interface AuthenticationFragment {
    void init(View view);
    boolean isReadyToProgress();
    void saveFragmentState();
}