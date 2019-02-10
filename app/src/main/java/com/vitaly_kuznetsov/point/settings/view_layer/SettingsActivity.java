package com.vitaly_kuznetsov.point.settings.view_layer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BaseContract;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BasicStateActionsFragment;
import com.vitaly_kuznetsov.point.base_models.reusable_fragments.CustomDialog;
import com.vitaly_kuznetsov.point.base_models.reusable_fragments.SignUpOneAuthenticationFragment;
import com.vitaly_kuznetsov.point.base_models.reusable_fragments.SignUpTwoAuthenticationFragment;
import com.vitaly_kuznetsov.point.base_models.reusable_fragments.UserCardFragment;
import com.vitaly_kuznetsov.point.home.view_layer.activities.HomeActivity;
import com.vitaly_kuznetsov.point.settings.presenter_layer.SettingsPresenter;

import java.util.ArrayList;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity
        implements BaseContract.View, BasicUiActionsSettings {

    private SettingsPresenter presenter;
    private FragmentManager fragmentManager;
    private ArrayList<Fragment> fragmentArrayList;

    //----------Change of View State-------------

    @Override
    public void init() {
        fragmentManager = getSupportFragmentManager();

        showFragments();

        findViewById(R.id.save_icon).setOnClickListener(view -> presenter.onSaveIconClicked());

        findViewById(R.id.go_back).setOnClickListener(view -> presenter.onBackPressed());

    }

    @Override
    public void showFragments() {
        if(fragmentArrayList != null){
            fragmentArrayList.clear();
        }

        fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new UserCardFragment());
        fragmentArrayList.add(new SignUpOneAuthenticationFragment());
        fragmentArrayList.add(new SignUpTwoAuthenticationFragment());

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.constraint_layout_user_card, fragmentArrayList.get(0));
        fragmentTransaction.add(R.id.constraint_layout_1, fragmentArrayList.get(1));
        fragmentTransaction.add(R.id.constraint_layout_2, fragmentArrayList.get(2));
        fragmentTransaction.commit();
    }

    @Override
    public void showAlertDialog(int alertType) {
        CustomDialog customDialog = new CustomDialog();
        customDialog.setAlert(alertType, presenter);
        customDialog.show(fragmentManager, "Alert");
    }

    @Override
    public void hideKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);

            Objects.requireNonNull(inputMethodManager).hideSoftInputFromWindow
                    (getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void showProgressBar() {
        ProgressDialog progressDialog = new ProgressDialog(SettingsActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
    }

    //----------Lifecycle Actions-----------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.presenter = new SettingsPresenter();
        presenter.attachView(this);

        init();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStopView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        presenter = null;
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    //--------------Intents-----------------
    @Override
    public void goToHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void goToHomeActivity(String errorText) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("Error", "Following Error occurred:" + errorText);

        startActivity(intent);
        finish();
    }

    //--------------Getters and Setters-----------------------

    @Override
    public BaseContract.Presenter getPresenter() {
        return presenter;
    }

    @Override
    public ArrayList<Fragment> getFragments() {
        return fragmentArrayList;
    }

    @Override
    public void saveFragmentsState() {
        for (Fragment fragment : fragmentArrayList)
            ((BasicStateActionsFragment) fragment).saveFragmentState();
    }
}