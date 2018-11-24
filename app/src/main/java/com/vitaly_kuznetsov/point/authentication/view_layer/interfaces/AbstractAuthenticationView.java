package com.vitaly_kuznetsov.point.authentication.view_layer.interfaces;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.authentication.presenter_layer.interfaces.AbstractAuthenticationPresenter;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BaseContract;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.PostModel;
import com.vitaly_kuznetsov.point.home.view_layer.activities.HomeActivity;
import com.vitaly_kuznetsov.point.main_activity.view_layer.MainActivity;

public abstract class AbstractAuthenticationView extends AppCompatActivity
        implements BaseContract.View, BasicUiActions {

    protected AbstractAuthenticationPresenter currentPresenter;
    protected FragmentManager fragmentManager;
    protected AuthenticationFragment currentFragment;

    //----------Change of View State-------------

    @Override
    public void init() {
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    public void showAuthenticationFragment(AuthenticationFragment newFragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (this.currentFragment == null) {
            fragmentTransaction.add(R.id.fragment_constraint_layout, (Fragment) newFragment);
        }
        else {
            this.currentFragment.saveFragmentState();
            fragmentTransaction.replace(R.id.fragment_constraint_layout, (Fragment) newFragment);
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    @Override
    public void removeAuthenticationFragment() {
        fragmentManager.popBackStack();
        currentPresenter.onBackPressed();
    }

    //----------Lifecycle Actions-----------

    @Override
    protected void onStop() {
        super.onStop();
        currentPresenter.onStopView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        currentPresenter.detachView();
        currentPresenter = null;
    }

    //------------Change of View through Intent---------------
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        currentPresenter.onBackPressed();
    }

    @Override
    public void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void goToHomeActivity(PostModel postModel) {
        Gson gson = new Gson();
        String intentJson = gson.toJson(postModel);

        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("Postmodel", intentJson);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    //-------------Basic UI Controllers--------------

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void showError() {
        if (currentFragment instanceof BasicCodeActionsFragment)
            ((BasicCodeActionsFragment) currentFragment).showError();
        else {
            TextView errorTextView = findViewById(R.id.error_text_view_0);
            errorTextView.setAlpha(1);
        }

        View view = findViewById(R.id.wait_for_touch_view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPresenter.onAfterError();
            }
        });
        view.setVisibility(View.VISIBLE);
        view.bringToFront();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void hideError() {
        if (currentFragment instanceof BasicCodeActionsFragment)
            ((BasicCodeActionsFragment) currentFragment).hideError();
        else {
            TextView errorTextView = findViewById(R.id.error_text_view_0);
            errorTextView.setAlpha(0);
        }

        View view = findViewById(R.id.wait_for_touch_view);
        view.setOnClickListener(null);
        view.setVisibility(View.GONE);
    }

    @Override
    public void showTryAgainTextView() {
        TextView tryAgainTextView = findViewById(R.id.try_again_text_view_0);
        tryAgainTextView.setAlpha(1);
        tryAgainTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPresenter.onTryAgainTextViewClicked();
            }
        });
    }

    @Override
    public void hideTryAgainTextView() {
        TextView tryAgainTextView = findViewById(R.id.try_again_text_view_0);
        tryAgainTextView.setAlpha(0);
        tryAgainTextView.setOnClickListener(null);
    }

    //---------------Getters and Setters--------------------
    @Override
    public AbstractAuthenticationPresenter getPresenter() {
        return this.currentPresenter;
    }

    @Override
    public AuthenticationFragment getFragment() {
        return this.currentFragment;
    }

    @Override
    public void setFragment(AuthenticationFragment fragment) {
        this.currentFragment = fragment;
    }
}
