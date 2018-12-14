package com.vitaly_kuznetsov.point.base_models.reusable_fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.settings.presenter_layer.SettingsPresenter;

import java.util.Objects;

public class CustomDialog extends DialogFragment {

    public final static int SAVE_ALERT = 0;
    public final static int EXIT_ALERT = 1;
    public final static int ERROR_ALERT = 2;

    private int title;
    private int message;
    private int alertType;
    private SettingsPresenter viewPresenter;

    public CustomDialog() {
        this.alertType = 0;
        this.title = R.string.alert_title_0;
        this.message = R.string.alert_text_0;
    }

    public void setAlert(int alertType, SettingsPresenter presenter) {
        this.alertType = alertType;
        this.viewPresenter = presenter;

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
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

        builder.setTitle(title)
                .setMessage(message)
                .setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        if (alertType != ERROR_ALERT)
            builder.setPositiveButton(R.string.accept_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    viewPresenter.onAlertAccepted(alertType);
                }
            });

        return builder.create();
    }
}