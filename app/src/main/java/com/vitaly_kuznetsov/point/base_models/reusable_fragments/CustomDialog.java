package com.vitaly_kuznetsov.point.base_models.reusable_fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BaseContract;
import com.vitaly_kuznetsov.point.settings.presenter_layer.SettingsPresenter;

import java.util.Objects;

public class CustomDialog extends DialogFragment {

    public final static int SAVE_ALERT = 0;
    public final static int EXIT_ALERT = 1;
    public final static int ERROR_ALERT = 2;
    public final static int EMPTY_FIELD= 3;

    private int title;
    private int message;
    private int alertType;

    private String messageNotDefault;
    private BaseContract.Presenter viewPresenter;

    public void setAlert(int alertType, BaseContract.Presenter presenter) {
        this.alertType = alertType;
        this.viewPresenter = presenter;
        messageNotDefault = null;

        if (alertType == SAVE_ALERT) {
            this.title = R.string.alert_title_0;
            this.message = R.string.alert_text_0;
        }
        else if (alertType == EXIT_ALERT) {
            this.title = R.string.alert_title_1;
            this.message = R.string.alert_text_1;
        }
        else if (alertType == ERROR_ALERT) {
            this.title = R.string.alert_title_2;
            this.message = R.string.alert_text_2;
        }
        else if (alertType == EMPTY_FIELD) {
            this.title = R.string.alert_title_3;
            this.message = R.string.alert_text_3;
        }
    }

    public void setAlert(int alertType, BaseContract.Presenter presenter, String message) {
        setAlert(alertType, presenter);
        if (alertType == ERROR_ALERT) this.messageNotDefault = "Following Error occurred: " + "\n" + message;
        else this.messageNotDefault = message;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

        builder.setTitle(title)
                .setMessage(message)
                .setNegativeButton(R.string.cancel_button, (dialog, id) -> dialog.cancel());
        if (messageNotDefault != null) builder.setMessage(messageNotDefault);

        if (alertType == ERROR_ALERT)
            builder.setNegativeButton("Ok", (dialog, id) -> dialog.cancel());
        else if (alertType != EMPTY_FIELD)
            builder.setPositiveButton(R.string.accept_button, (dialog, id) -> ((SettingsPresenter) viewPresenter).onAlertAccepted(alertType));

        return builder.create();
    }
}