package com.vitaly_kuznetsov.point.authentication.view_layer.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.authentication.presenter_layer.interfaces.AuthenticationPresenterInterface;
import com.vitaly_kuznetsov.point.authentication.presenter_layer.presenters.LogInViewPresenter;
import com.vitaly_kuznetsov.point.authentication.view_layer.fragments.GetCodeFragment;
import com.vitaly_kuznetsov.point.authentication.view_layer.fragments.SendCodeFragment;
import com.vitaly_kuznetsov.point.authentication.view_layer.interfaces.AuthenticationFragment;
import com.vitaly_kuznetsov.point.authentication.view_layer.interfaces.LogInView;
import com.vitaly_kuznetsov.point.launch_app.view_layer.MainActivity;

public class LogInActivity extends AppCompatActivity implements LogInView {

    private LogInViewPresenter logInViewPresenter;
    private FragmentManager fragmentManager;
    private AuthenticationFragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        logInViewPresenter = new LogInViewPresenter();
        logInViewPresenter.attachView(this);

        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logInViewPresenter.detachView();
        logInViewPresenter = null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        logInViewPresenter.applyUserDataModelChanges();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (currentFragment instanceof SendCodeFragment) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove((Fragment) currentFragment);
            fragmentTransaction.commit();
            currentFragment = (AuthenticationFragment)
                    (fragmentManager.findFragmentById(R.id.fragment_constraint_layout));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        logInViewPresenter.onBackPressed();

        if (!(currentFragment instanceof GetCodeFragment)) {
            hideError();
            currentFragment = (AuthenticationFragment)
                    (fragmentManager.findFragmentById(R.id.fragment_constraint_layout));
        }
    }

    @Override
    public void changeAuthenticationActivity() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    public void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void showTryAgainTextView() {
        TextView tryAgainTextView = findViewById(R.id.try_again_text_view_0);
        tryAgainTextView.setAlpha(1);
        tryAgainTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logInViewPresenter.onTryAgainTextViewClicked();
            }
        });
    }

    @Override
    public void hideTryAgainTextView() {
        TextView tryAgainTextView = findViewById(R.id.try_again_text_view_0);
        tryAgainTextView.setAlpha(0);
        tryAgainTextView.setOnClickListener(null);
    }

    @Override
    public void showError() {

    }

    @Override
    public void hideError() {

    }

    @Override
    public void showAuthenticationFragment(Fragment newFragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (this.currentFragment == null) {
            fragmentTransaction.add(R.id.fragment_constraint_layout, newFragment);
        }
        else {
            this.currentFragment.saveFragmentState();
            fragmentTransaction.replace(R.id.fragment_constraint_layout, newFragment);
            fragmentTransaction.addToBackStack(null);
        }

        this.currentFragment = (AuthenticationFragment) newFragment;
        fragmentTransaction.commit();
    }

    @Override
    public AuthenticationPresenterInterface getPresenter() {
        return logInViewPresenter;
    }

    @Override
    public AuthenticationFragment getFragment() {
        return currentFragment;
    }

    @Override
    public void init() {
        fragmentManager = getSupportFragmentManager();
        TextView changeActivityTextView = findViewById(R.id.change_activity_text_view);
        ImageView goBackButton = findViewById(R.id.go_back_icon);

        changeActivityTextView.setPaintFlags
                (changeActivityTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        changeActivityTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logInViewPresenter.onChangeActivityClicked();
            }
        });

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logInViewPresenter.onGoBackClicked();
            }
        });

        logInViewPresenter.onNextFragmentClicked();
    }

}
