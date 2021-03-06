package com.vitaly_kuznetsov.point.main_activity.view_layer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.authentication.view_layer.activities.LogInActivity;
import com.vitaly_kuznetsov.point.authentication.view_layer.activities.SignUpActivity;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BaseContract;
import com.vitaly_kuznetsov.point.main_activity.presenter_layer.MainViewPresenter;

public class MainActivity extends AppCompatActivity implements MainView {

    private MainViewPresenter mainViewPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewPresenter = new MainViewPresenter();
        mainViewPresenter.attachView(this);

        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainViewPresenter.detachView();
        mainViewPresenter = null;
    }

    @Override
    public void goToLogInActivity() {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }

    @Override
    public void goToSignUpActivity() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    public void init() {

        Button logInButton = findViewById(R.id.log_in_button_0);
        Button signUpButton = findViewById(R.id.sign_up_button_0);

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewPresenter.onLogInClicked();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewPresenter.onSignUpClicked();
            }
        });
    }

    @Override
    public BaseContract.Presenter getPresenter() {
        return mainViewPresenter;
    }
}
