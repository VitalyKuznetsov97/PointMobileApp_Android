package com.vitaly_kuznetsov.point.home.view_layer.activities;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.base_models.custom_views.PointMainButton;
import com.vitaly_kuznetsov.point.home.view_layer.fragments.SearchFragment;
import com.vitaly_kuznetsov.point.home.view_layer.fragments.SettingsFragment;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

public class HomeActivity extends AppCompatActivity {

    private ToggleButton settings;
    private ToggleButton home;
    private ToggleButton messages;
    private ConstraintLayout constraintLayoutSettings;
    private View view3, view4;
    private ConstraintLayout constraintLayoutMessages;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String postModel = getIntent().getStringExtra("Postmodel");
        String error = getIntent().getStringExtra("Error");
        String noConnection = getIntent().getStringExtra("No_Connection");
        textView = findViewById(R.id.text);

        if (postModel != null)
            textView.setText(postModel);
        else if (error != null)
            textView.setText(error);
        else if (noConnection != null)
            textView.setText("No connection: " + noConnection);
        else
            textView.setText("????");

        init();
    }

    private void init(){
        settings = findViewById(R.id.settings_button);
        home = findViewById(R.id.home_button);
        messages = findViewById(R.id.messages_button);

        constraintLayoutSettings = findViewById(R.id.settings_layout);
        view3 = findViewById(R.id.view_3);
        view4 = findViewById(R.id.view_4);
        constraintLayoutMessages = findViewById(R.id.messages_layout);

        constraintLayoutSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settings.setChecked(true);
            }
        });

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home.setChecked(true);
            }
        };
        view3.setOnClickListener(clickListener);
        view4.setOnClickListener(clickListener);

        constraintLayoutMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messages.setChecked(true);
            }
        });

        settings.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    home.setChecked(false);
                    messages.setChecked(false);
                    settings.setEnabled(false);
                    showFrag(new SettingsFragment());
                }
                else settings.setEnabled(true);
            }
        });

        home.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    settings.setChecked(false);
                    messages.setChecked(false);
                    home.setEnabled(false);
                    showFrag(new SearchFragment());
                }
                else home.setEnabled(true);
            }
        });

        messages.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    home.setChecked(false);
                    settings.setChecked(false);
                    messages.setEnabled(false);
                }
                else messages.setEnabled(true);
            }
        });

        home.setChecked(true);
    }

    private void showFrag(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_constraint_layout, fragment);
        fragmentTransaction.commit();
    }
}