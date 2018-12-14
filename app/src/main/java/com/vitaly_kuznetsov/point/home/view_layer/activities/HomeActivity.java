package com.vitaly_kuznetsov.point.home.view_layer.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.base_models.custom_views.CheckableImageView;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.Data;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.PostModel;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.PostPayload;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.UserData;
import com.vitaly_kuznetsov.point.base_models.user_data_model.model.UserDataModel;
import com.vitaly_kuznetsov.point.base_models.user_data_model.user_model_preferences.ModelHandler;
import com.vitaly_kuznetsov.point.home.presenter_layer.HomeViewPresenter;
import com.vitaly_kuznetsov.point.home.view_layer.fragments.ChatPreviewEmptyFragment;
import com.vitaly_kuznetsov.point.home.view_layer.fragments.SettingsFragment;
import com.vitaly_kuznetsov.point.home.view_layer.interfaces.BasicUiActionsHome;
import com.vitaly_kuznetsov.point.home.view_layer.fragments.ChatPreviewFragment;
import com.vitaly_kuznetsov.point.search.view_layer.SearchFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements BasicUiActionsHome {

    private CheckableImageView settings;
    private CheckableImageView home;
    private CheckableImageView messages;

    private Fragment currentFragment;
    private HomeViewPresenter homeViewPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeViewPresenter = new HomeViewPresenter();

        String postModel = getIntent().getStringExtra("Postmodel");
        String error = getIntent().getStringExtra("Error");
        TextView textView = findViewById(R.id.text);

        if (postModel != null) {
            UserDataModel userDataModel = ModelHandler.getInstance(this);
            Gson gson = new Gson();
            PostModel model = gson.fromJson(postModel, PostModel.class);
            PostPayload payload = model.getPayload();
            Data data = payload.getData();
            UserData userData = data.getUserData();
            userDataModel.setNickname(userData.getNickname());
            userDataModel.setMyGender(Integer.parseInt(userData.getMyGender()));
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            try {
                userDataModel.setMyAge(dateFormat.parse(userData.getMyAge()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            userDataModel.setMyBio(userData.getMyBio());
            userDataModel.setYourGender(Integer.parseInt(userData.getYourGender()));
            userDataModel.setYourAgeString(userData.getYourAge());
            userDataModel.setToken(data.getToken());
            ModelHandler.changeUserDataModel(userDataModel);
        }
        else if (error != null)
            textView.setText(error);
        else
            textView.setText("????");

        init();
    }

    private void init(){
        settings = findViewById(R.id.settings_button);
        home = findViewById(R.id.home_button);
        messages = findViewById(R.id.messages_button);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (settings.isChecked()) {
                    home.setChecked(false);
                    messages.setChecked(false);
                    showFrag(new SettingsFragment());
                }
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (home.isChecked()) {
                    settings.setChecked(false);
                    messages.setChecked(false);
                    showFrag(new SearchFragment());
                }
            }
        });

        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (messages.isChecked()) {
                    settings.setChecked(false);
                    home.setChecked(false);
                    showFrag(new ChatPreviewEmptyFragment());
                }
            }
        });

        home.setChecked(true);
        showFrag(new SearchFragment());
    }

    private void showFrag(Fragment fragment){
        if (currentFragment == null || currentFragment.getClass() != fragment.getClass()) {
            currentFragment = fragment;

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.fragment_constraint_layout, fragment);
            fragmentTransaction.commit();
        }
    }

    public UserDataModel getUserDataModel() {
        return ModelHandler.getInstance(this);
    }

    @Override
    public HomeViewPresenter getCurrentPresenter() {
        return homeViewPresenter;
    }

    @Override
    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    @Override
    public void setCurrentFragment(Fragment fragment) {
        currentFragment = fragment;
    }
}