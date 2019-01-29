package com.vitaly_kuznetsov.point.authentication.view_layer.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import com.vitaly_kuznetsov.point.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getActivity()),
                this, 1, 1, 2000);
        datePickerDialog.getDatePicker().setMinDate(-1577934000000L);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime() - 568024668000L);
        datePickerDialog.getDatePicker().updateDate( 2000, 1, 1);
        return datePickerDialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        TextView textView = Objects.requireNonNull(getActivity()).findViewById(R.id.calendar_text_view);
        textView.setText(dateFormat.format(calendar.getTime()));
    }
}