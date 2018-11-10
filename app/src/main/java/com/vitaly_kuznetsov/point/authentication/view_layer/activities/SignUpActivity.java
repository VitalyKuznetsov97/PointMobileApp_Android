package com.vitaly_kuznetsov.point.authentication.view_layer.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.authentication.presenter_layer.interfaces.AuthenticationPresenterInterface;
import com.vitaly_kuznetsov.point.authentication.presenter_layer.interfaces.SignUpPresenterInterface;
import com.vitaly_kuznetsov.point.authentication.presenter_layer.presenters.SignUpViewPresenter;
import com.vitaly_kuznetsov.point.authentication.view_layer.fragments.SendCodeFragment;
import com.vitaly_kuznetsov.point.authentication.view_layer.fragments.SignUpOneFragment;
import com.vitaly_kuznetsov.point.authentication.view_layer.interfaces.AuthenticationFragment;
import com.vitaly_kuznetsov.point.authentication.view_layer.interfaces.SignUpView;
import com.vitaly_kuznetsov.point.launch_app.view_layer.MainActivity;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity implements SignUpView {

    private SignUpPresenterInterface signUpViewPresenter;
    private FragmentManager fragmentManager;
    private AuthenticationFragment currentFragment;
    private ArrayList<ImageView> stepArrayList;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpViewPresenter = new SignUpViewPresenter();
        signUpViewPresenter.attachView(this);

        init();
    }

    @Override
    protected void onStop() {
        super.onStop();
        signUpViewPresenter.applyUserDataModelChanges();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
            if (fragmentManager.getFragments().size() != 0 ||
                    fragmentManager.getFragments().get(fragmentManager.getBackStackEntryCount() - 1) == null){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_constraint_layout,
                    (Fragment) currentFragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        if (currentFragment instanceof SendCodeFragment) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove((Fragment) currentFragment);
            fragmentTransaction.commit();
            currentFragment = (AuthenticationFragment)
                    (fragmentManager.findFragmentById(R.id.fragment_constraint_layout));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        signUpViewPresenter.detachView();
        signUpViewPresenter = null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        signUpViewPresenter.onBackPressed();

        if (!(currentFragment instanceof SignUpOneFragment)) {
            hideError();
            currentFragment = (AuthenticationFragment)
                    (fragmentManager.findFragmentById(R.id.fragment_constraint_layout));
        }
    }

    @Override
    public void setDescriptionText(int textId){
        TextView textView = findViewById(R.id.description_0);
        textView.setText(textId);
    }

    @Override
    public void changeAuthenticationActivity() {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }

    @Override
    public void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void showError() {
        TextView errorTextView = findViewById(R.id.error_text_view_0);
        errorTextView.setAlpha(1);
        nextButton.setActivated(true);

        View view = findViewById(R.id.wait_for_touch_view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpViewPresenter.onAfterError();
            }
        });
        view.setVisibility(View.VISIBLE);
        view.bringToFront();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void hideError() {
        TextView errorTextView = findViewById(R.id.error_text_view_0);
        errorTextView.setAlpha(0);
        nextButton.setActivated(false);

        View view = findViewById(R.id.wait_for_touch_view);
        view.setOnClickListener(null);
        view.setVisibility(View.GONE);
    }

    @Override
    public void showSkipButton() {
        TextView skipTextView = findViewById(R.id.skip_text_view);
        skipTextView.setAlpha(1);
        skipTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpViewPresenter.onSkipTextViewClicked();
            }
        });
    }

    @Override
    public void hideSkipButton() {
        TextView skipTextView = findViewById(R.id.skip_text_view);
        skipTextView.setAlpha(0);
        skipTextView.setOnClickListener(null);
    }

    @Override
    public void showNextButton() {
        nextButton.setAlpha(1);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpViewPresenter.onNextFragmentClicked();
            }
        });
    }

    @Override
    public void hideNextButton() {
        Button nextButton = findViewById(R.id.next_button);
        nextButton.setAlpha(0);
        nextButton.setOnClickListener(null);
    }

    @Override
    public void showTryAgainTextView() {
        TextView tryAgainTextView = findViewById(R.id.try_again_text_view_0);
        tryAgainTextView.setAlpha(1);
        tryAgainTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpViewPresenter.onTryAgainTextViewClicked();
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
        return signUpViewPresenter;
    }

    @Override
    public AuthenticationFragment getFragment() {
        return currentFragment;
    }

    @Override
    public void setSteps(int step){
        for (int i = 0; i < 3; i++){
            stepArrayList.get(i).setActivated(i <= step);
        }
    }

    @Override
    public void init() {

        TextView changeActivityTextView = findViewById(R.id.change_activity_text_view);
        ImageView goBackButton = findViewById(R.id.go_back_icon);
        fragmentManager = getSupportFragmentManager();
        nextButton = findViewById(R.id.next_button);
        setStepsArray();

        changeActivityTextView.setPaintFlags
                (changeActivityTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        changeActivityTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpViewPresenter.onChangeActivityClicked();
            }
        });

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpViewPresenter.onGoBackClicked();
            }
        });

        signUpViewPresenter.onNextFragmentClicked();
    }

    private void setStepsArray(){
        ImageView indicator0 = findViewById(R.id.indicator_0);
        ImageView indicator1 = findViewById(R.id.indicator_1);
        ImageView indicator2 = findViewById(R.id.indicator_2);

        stepArrayList = new ArrayList<>();
        stepArrayList.add(indicator0);
        stepArrayList.add(indicator1);
        stepArrayList.add(indicator2);
    }
}