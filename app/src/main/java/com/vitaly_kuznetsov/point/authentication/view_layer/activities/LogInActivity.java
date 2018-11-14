package com.vitaly_kuznetsov.point.authentication.view_layer.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.authentication.presenter_layer.presenters.LogInViewPresenter;
import com.vitaly_kuznetsov.point.authentication.view_layer.interfaces.AbstractAuthenticationView;

public class LogInActivity extends AbstractAuthenticationView {

    //----------Lifecycle Actions-----------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        currentPresenter = new LogInViewPresenter();
        currentPresenter.attachView(this);

        init();
    }

    //----------Sign Up View Intent-------------

    @Override
    public void changeAuthenticationActivity() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    //----------Initialize view after Presenter attachment----------------

    @Override
    public void init() {
        super.init();

        TextView changeActivityTextView = findViewById(R.id.change_activity_text_view);
        ImageView goBackButton = findViewById(R.id.go_back_icon);

        changeActivityTextView.setPaintFlags
                (changeActivityTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        changeActivityTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPresenter.onChangeActivityClicked();
            }
        });

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPresenter.onGoBackClicked();
            }
        });

        currentPresenter.onNextFragmentClicked();
    }
}