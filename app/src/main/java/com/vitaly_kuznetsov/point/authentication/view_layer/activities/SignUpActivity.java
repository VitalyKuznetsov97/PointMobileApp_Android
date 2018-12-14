package com.vitaly_kuznetsov.point.authentication.view_layer.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.authentication.presenter_layer.presenters.SignUpViewPresenter;
import com.vitaly_kuznetsov.point.authentication.view_layer.interfaces.AbstractAuthenticationView;
import com.vitaly_kuznetsov.point.authentication.view_layer.interfaces.BasicCodeActionsFragment;
import com.vitaly_kuznetsov.point.authentication.view_layer.interfaces.BasicUiActionsSignUp;

import java.util.ArrayList;

public class SignUpActivity extends AbstractAuthenticationView implements BasicUiActionsSignUp {

    private ArrayList<ImageView> stepArrayList;
    private Button nextButton;

    //----------Lifecycle Actions-----------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        this.currentPresenter = new SignUpViewPresenter();
        currentPresenter.attachView(this);

        init();
    }

    //----------Log In View Intent-------------
    @Override
    public void changeAuthenticationActivity() {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }

    //----------Initialize view after Presenter attachment----------------
    @Override
    public void init() {
        super.init();
        TextView changeActivityTextView = findViewById(R.id.change_activity_text_view);
        ImageView goBackButton = findViewById(R.id.go_back_icon);
        nextButton = findViewById(R.id.next_button);
        setStepsArray();

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

    private void setStepsArray(){
        ImageView indicator0 = findViewById(R.id.indicator_0);
        ImageView indicator1 = findViewById(R.id.indicator_1);
        ImageView indicator2 = findViewById(R.id.indicator_2);

        stepArrayList = new ArrayList<>();
        stepArrayList.add(indicator0);
        stepArrayList.add(indicator1);
        stepArrayList.add(indicator2);
    }

    //-----------Sign Up View UI Actions Implementation-------------

    @Override
    public void setDescriptionText(int textId){
        TextView textView = findViewById(R.id.description_0);
        textView.setText(textId);
    }

    @Override
    public void showError() {
        super.showError();
        if (!(currentFragment instanceof BasicCodeActionsFragment))
            nextButton.setActivated(true);
    }

    @Override
    public void hideError() {
        super.hideError();
        if (!(currentFragment instanceof BasicCodeActionsFragment))
            nextButton.setActivated(false);
    }

    @Override
    public void showSkipButton() {
        TextView skipTextView = findViewById(R.id.skip_text_view);
        skipTextView.setAlpha(1);
        skipTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SignUpViewPresenter) currentPresenter).onSkipTextViewClicked();
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
                currentPresenter.onNextFragmentClicked();
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
    public void setSteps(int step){
        for (int i = 0; i < 3; i++){
            stepArrayList.get(i).setActivated(i <= step);
        }
    }
}