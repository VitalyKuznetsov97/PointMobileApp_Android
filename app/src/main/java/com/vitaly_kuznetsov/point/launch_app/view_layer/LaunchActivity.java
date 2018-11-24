package com.vitaly_kuznetsov.point.launch_app.view_layer;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.base_models.server_rest_api.post_models.PostModel;
import com.vitaly_kuznetsov.point.home.view_layer.activities.HomeActivity;
import com.vitaly_kuznetsov.point.launch_app.presenter_layer.LaunchViewPresenter;
import com.vitaly_kuznetsov.point.launch_app.presenter_layer.LaunchViewPresenterInterface;
import com.vitaly_kuznetsov.point.main_activity.view_layer.MainActivity;

public class LaunchActivity extends AppCompatActivity implements LaunchViewInterface {

    private LaunchTimer launchTimer;
    private Gson gson;
    private LaunchViewPresenterInterface currentPresenter;

    //------------------Lifecycle Actions--------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        currentPresenter.detachView();
        currentPresenter = null;
    }

    //----------Initialize view after Presenter attachment----------------

    @Override
    public void init() {
        gson = new Gson();
        launchTimer = new LaunchTimer();

        currentPresenter = new LaunchViewPresenter();
        currentPresenter.attachView(this);
    }

    //------------UI Actions-----------------

    @Override
    public void startTimer() {
        launchTimer.start();
    }

    @Override
    public void stopTimer() {
        launchTimer.cancel();
        launchTimer.onFinish();
    }

    //------------Intents---------------

    @Override
    public void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void goToHomeActivity(PostModel postModel) {
        String intentJson = gson.toJson(postModel);

        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("Postmodel", intentJson);

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

    @Override
    public void goToHomeActivityOnFailure(String errorText) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("No_Connection", "Following Error occurred:" + errorText);

        startActivity(intent);
        finish();
    }

    //------------Timer class----------------

    private final class LaunchTimer extends CountDownTimer {

        private final static long LAUNCH_TIMER_INTERVAL = 10;
        private final static long LAUNCH_TIMER_MILLIS_UNTIL_FINISH = LAUNCH_TIMER_INTERVAL * 100;

        private ProgressBar progressBar;

        //-------------Implemented methods-----------------

        private LaunchTimer() {
            super(LAUNCH_TIMER_MILLIS_UNTIL_FINISH, LAUNCH_TIMER_INTERVAL);
            progressBar = findViewById(R.id.progressBar);
        }

        @Override
        public void onTick(long millis) {
            progressBar.setProgress((int) (100 - millis / LAUNCH_TIMER_INTERVAL));
        }

        @Override
        public void onFinish() {
            progressBar.setProgress(100);
        }

    }
}