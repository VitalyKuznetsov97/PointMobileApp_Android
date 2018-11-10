package com.vitaly_kuznetsov.point.authentication.view_layer.interfaces;

public interface SignUpView extends LogInView {

    void setSteps(int step);
    void showSkipButton();
    void hideSkipButton();
    void showNextButton();
    void hideNextButton();
    void setDescriptionText(int text);

}
