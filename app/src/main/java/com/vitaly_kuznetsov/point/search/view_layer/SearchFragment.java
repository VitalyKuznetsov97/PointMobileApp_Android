package com.vitaly_kuznetsov.point.search.view_layer;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.base_models.custom_views.PointMainButton;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BasicFragmentInterface;
import com.vitaly_kuznetsov.point.home.view_layer.activities.HomeActivity;
import com.vitaly_kuznetsov.point.search.presenter_layer.SearchFragmentPresenter;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

public class SearchFragment extends Fragment implements BasicFragmentInterface, SearchFragmentInterface {

    private ConstraintLayout fragmentLayout;
    private ArrayList<ImageView> imageViewArrayList;
    private PointMainButton pointMainButton;
    private TextView searchText;

    private Context context;

    private static final float RING_FINAL_WIDTH_MULTIPLIER = 2.411F;
    private static final float RING_THICKNESS_DIVIDER = 4;
    private int animationDuration;
    private long duration;
    private ViewGroup.LayoutParams layoutParams;

    private Disposable disposableInterval;

    private SearchFragmentPresenter presenter;

    //--------------Lifecycle Actions----------------

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        init(view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        presenter = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onFragmentResumed();
    }

    //--------------Initialize Fragment upon creation----------------

    @Override
    public void init(View view) {

        //variables init
        pointMainButton = view.findViewById(R.id.point_main_button);
        searchText = view.findViewById(R.id.text_description_0);
        fragmentLayout = view.findViewById(R.id.constraint_layout_0);
        imageViewArrayList = new ArrayList<>();
        pointMainButton.setOnCheckedChangeListener((compoundButton, b) -> presenter.onMainButtonCheckedChanged(b));
        context = view.getContext();
        if (presenter == null) presenter = new SearchFragmentPresenter();
        presenter.attachView(this);

        //setting up animation
        Display display = Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        float width;
        if (size.x <= 250){
            pointMainButton.setWidth(250);
            pointMainButton.setHeight(250);
            width = (int) (size.x * 0.456 * PointMainButton.SCALE) / 2;
        }
        else width = (int) (250 * 0.456 * PointMainButton.SCALE) / 2;

        float speed = (width * (RING_FINAL_WIDTH_MULTIPLIER - 1)) / 5;
        duration = (long) (((width / RING_THICKNESS_DIVIDER) / speed) * 1000);
        animationDuration = (int) (duration * 5);
    }

    @Override
    public void attachPresenter(SearchFragmentPresenter searchFragmentPresenter) {
        this.presenter = searchFragmentPresenter;
    }

    @Override
    public boolean isReadyToProgress() {
        return true;
    }

    @Override
    public void setPointMainButtonChecked(boolean flag) {
        pointMainButton.setChecked(flag);
    }

    @Override
    public void stopAnimation(){
        disposableInterval.dispose();
        for (int i = 0; i < 5; i++){
            fragmentLayout.removeView(imageViewArrayList.get(i));
            imageViewArrayList.get(i).clearAnimation();
        }
        imageViewArrayList.clear();

        searchText.setText(getResources().getText(R.string.search_description_0));
    }

    @Override
    public void startAnimation(){

        if (layoutParams == null){
            View ringView = Objects.requireNonNull(getActivity()).findViewById(R.id.ring_view);
            layoutParams = ringView.getLayoutParams();
            fragmentLayout.removeView(ringView);
        }

        for (int i = 0; i < 5; i++) {
            ImageView expandableView = new ImageView(context);
            expandableView.setImageResource(R.drawable.expandable_ring);

            expandableView.setLayoutParams(layoutParams);
            imageViewArrayList.add(expandableView);
            fragmentLayout.addView(expandableView);
        }

        Predicate<Long> isLessThanFive = seconds -> seconds < 5;

        final Consumer<Long> createRing = attempt -> {
            ScaleAnimation animation =
                    new ScaleAnimation(1.0F, RING_FINAL_WIDTH_MULTIPLIER, 1.0F, RING_FINAL_WIDTH_MULTIPLIER,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.6F, 0);

            animation.setRepeatCount(Animation.INFINITE);
            alphaAnimation.setRepeatCount(Animation.INFINITE);

            AnimationSet animationSet = new AnimationSet(true);
            animationSet.addAnimation(animation);
            animationSet.addAnimation(alphaAnimation);
            animationSet.setDuration(animationDuration);
            animationSet.setRepeatMode(Animation.RESTART);
            animationSet.setInterpolator(new LinearInterpolator());

            int i = attempt.intValue();

            imageViewArrayList.get(i).startAnimation(animationSet);
        };

        Observable<Long> observable = Observable.interval(0, duration, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .takeWhile(isLessThanFive).doOnNext(createRing);

        disposableInterval = observable.subscribe(success ->{}, Throwable::printStackTrace);

        searchText.setText("");
    }

    //-----------------------Getter methods------------------------------

    @Override
    public HomeActivity getHomeActivity() {
        return (HomeActivity) getActivity();
    }

    @Override
    public SearchFragmentPresenter getPresenter() {
        return presenter;
    }

    public boolean isPointMainButtonChecked() {
        return pointMainButton.isChecked();
    }
}