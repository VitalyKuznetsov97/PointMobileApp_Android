package com.vitaly_kuznetsov.point.home.view_layer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.base_models.custom_views.CheckableImageView;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BaseContract;
import com.vitaly_kuznetsov.point.base_models.reusable_fragments.CustomDialog;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.Data;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.PostModel;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.PostPayload;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.UserData;
import com.vitaly_kuznetsov.point.base_models.user_data_model.model.UserDataModel;
import com.vitaly_kuznetsov.point.base_models.user_data_model.user_model_preferences.ModelHandler;
import com.vitaly_kuznetsov.point.chat.view_layer.ChatActivity;
import com.vitaly_kuznetsov.point.chat_preview.presenter_layer.ChatPreviewViewPresenter;
import com.vitaly_kuznetsov.point.chat_preview.view_layer.ChatPreviewFragment;
import com.vitaly_kuznetsov.point.home.presenter_layer.HomeViewPresenter;
import com.vitaly_kuznetsov.point.home.view_layer.fragments.SettingsFragment;
import com.vitaly_kuznetsov.point.home.view_layer.interfaces.BasicUiActionsHome;
import com.vitaly_kuznetsov.point.search.view_layer.MatchFragment;
import com.vitaly_kuznetsov.point.search.view_layer.SearchFragment;

import static com.vitaly_kuznetsov.point.base_models.reusable_fragments.CustomDialog.ERROR_ALERT;

public class HomeActivity extends AppCompatActivity implements BaseContract.View, BasicUiActionsHome {

    private CheckableImageView settings;
    private CheckableImageView home;
    private CheckableImageView messages;

    private Fragment currentFragment;
    private HomeViewPresenter homeViewPresenter;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeViewPresenter = new HomeViewPresenter();
        homeViewPresenter.attachView(this);

        String postModel = getIntent().getStringExtra("Postmodel");
        String error = getIntent().getStringExtra("Error");
        String pointInfo = getIntent().getStringExtra("Point Info");

        if (postModel != null) {
            UserDataModel userDataModel = ModelHandler.getInstance(this);
            Gson gson = new Gson();
            PostModel model = gson.fromJson(postModel, PostModel.class);
            PostPayload payload = model.getPayload();
            Data data = payload.getData();
            UserData userData = data.getUserData();
            userDataModel.setNickname(userData.getNickname());
            userDataModel.setMyGender(Integer.parseInt(userData.getMyGender()));
            userDataModel.setMyAgeTimeStamp(Long.parseLong(userData.getMyAge()));
            userDataModel.setMyBio(userData.getMyBio());
            userDataModel.setYourGender(Integer.parseInt(userData.getYourGender()));
            userDataModel.setYourAgeString(userData.getYourAge());
            if (!userDataModel.getToken().equals(""))
                userDataModel.setToken(userDataModel.getToken());
            ModelHandler.changeUserDataModel(userDataModel);
        }
        else if (error != null)
            showAlertDialog(ERROR_ALERT, error);
        else if (pointInfo != null)
            showToastMessage(pointInfo);

        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (currentFragment instanceof MatchFragment){
            home.setChecked(true);
            showFrag(new SearchFragment());
        }
    }

    @Override
    public void onBackPressed() {
        if (currentFragment instanceof  MatchFragment)
            ((MatchFragment) currentFragment).getPresenter().onCancelButtonClicked();
        else super.onBackPressed();
    }

    @Override
    public void init(){
        settings = findViewById(R.id.settings_button);
        home = findViewById(R.id.home_button);
        messages = findViewById(R.id.messages_button);

        settings.setOnClickListener(view -> {
            if (settings.isChecked()) {
                hideLoading();
                home.setChecked(false);
                messages.setChecked(false);
                showFrag(new SettingsFragment());
            }
        });

        home.setOnClickListener(view -> {
            if (home.isChecked()) {
                hideLoading();
                settings.setChecked(false);
                messages.setChecked(false);
                showFrag(new SearchFragment());
            }
        });

        messages.setOnClickListener(view -> {
            if (messages.isChecked()) {
                hideLoading();
                settings.setChecked(false);
                home.setChecked(false);
                if (!(currentFragment instanceof ChatPreviewFragment)) showChatPreview();
            }
        });

        home.setChecked(true);
        showFrag(new SearchFragment());
    }

    public void showChatPreview(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(currentFragment);
        fragmentTransaction.commit();

        currentFragment = null;

        ChatPreviewFragment chatPreviewFragment = new ChatPreviewFragment();
        chatPreviewFragment.attachPresenter(this, new ChatPreviewViewPresenter());
    }

    public void showLoading(){
        View view = findViewById(R.id.loading_view);

        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyle);
        progressBar.setLayoutParams(view.getLayoutParams());

        ConstraintLayout constraintLayout = findViewById(R.id.fragment_constraint_layout);
        constraintLayout.addView(progressBar);
    }

    public void hideLoading(){
        if (progressBar != null){
            ConstraintLayout constraintLayout = findViewById(R.id.fragment_constraint_layout);
            constraintLayout.removeView(progressBar);
        }
    }

    public void goToChatActivity(String chat, boolean shouldStart){
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("Chat", chat);
        intent.putExtra("Should Start", shouldStart);
        startActivity(intent);
    }

    public void showFrag(Fragment fragment){
        if (currentFragment == null || currentFragment.getClass() != fragment.getClass()) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            if (currentFragment != null) fragmentTransaction.remove(currentFragment);
            fragmentTransaction.add(R.id.fragment_constraint_layout, fragment);
            fragmentTransaction.commit();

            currentFragment = fragment;

        }
    }

    public void showToastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public BaseContract.Presenter getPresenter() {
        return homeViewPresenter;
    }

    @Override
    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    @Override
    public void showAlertDialog(int alertType, String message) {
        CustomDialog customDialog = new CustomDialog();
        customDialog.setAlert(alertType, homeViewPresenter, message);
        customDialog.show(getSupportFragmentManager(), "Alert");
    }
}