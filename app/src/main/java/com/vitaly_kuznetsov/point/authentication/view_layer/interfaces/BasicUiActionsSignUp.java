package com.vitaly_kuznetsov.point.authentication.view_layer.interfaces;

public interface BasicUiActionsSignUp {

    //Set steps, equivalent to currentStep
    void setSteps(int currentStep);

    //Show or hide buttons
    void showSkipButton();
    void hideSkipButton();
    void showNextButton();
    void hideNextButton();

    //Change description text
    void setDescriptionText(int text);
}
