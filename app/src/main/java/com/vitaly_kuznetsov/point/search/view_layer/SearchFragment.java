package com.vitaly_kuznetsov.point.search.view_layer;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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

import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.base_models.custom_views.PointMainButton;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BaseContract;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BasicFragmentInterface;
import com.vitaly_kuznetsov.point.base_models.reusable_fragments.CustomDialog;
import com.vitaly_kuznetsov.point.home.view_layer.interfaces.BasicUiActionsHome;
import com.vitaly_kuznetsov.point.home.view_layer.interfaces.ChatPreviewFragmentInterface;
import com.vitaly_kuznetsov.point.search.presenter_layer.SearchFragmentPresenter;
import com.vitaly_kuznetsov.point.search.presenter_layer.SearchFragmentPresenterInterface;

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
    private View ringView;
    private ArrayList<ImageView> imageViewArrayList;
    private float width = 0;
    private int animationDuration;
    private ViewGroup.LayoutParams layoutParams;
    private PointMainButton pointMainButton;
    private TextView searchText;

    private Context context;

    private static final float RING_FINAL_WIDTH_MULTIPLIER = 2.411F;
    private static final float RING_THICKNESS_DIVIDER = 4;

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
    public void onStop() {
        super.onStop();
        pointMainButton.setChecked(false);
        pointMainButton.stopAnimation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        presenter = null;
    }

    //--------------Initialize Fragment upon creation----------------

    @Override
    public void init(View view) {

        pointMainButton = view.findViewById(R.id.point_main_button);

        ringView = view.findViewById(R.id.ring_view);
        layoutParams = ringView.getLayoutParams();
        layoutParams.height *= PointMainButton.SCALE;
        layoutParams.width *= PointMainButton.SCALE;

        searchText = view.findViewById(R.id.text_description_0);
        fragmentLayout = view.findViewById(R.id.constraint_layout_0);
        imageViewArrayList = new ArrayList<>();
        pointMainButton.setOnCheckedChangeListener((compoundButton, b) -> presenter.onMainButtonCheckedChanged(b));

        context = view.getContext();

        presenter = new SearchFragmentPresenter();
        presenter.attachView(this);
    }

    @Override
    public boolean isReadyToProgress() {
        return true;
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
    public void showAlertDialog(int alertType) {
        ((BasicUiActionsHome) Objects.requireNonNull(getActivity())).showAlertDialog(alertType);
    }

    @Override
    public void startAnimation(){

        if (width == 0){
            width = ringView.getWidth() * PointMainButton.SCALE;
            width /= 2;
            fragmentLayout.removeView(ringView);
        }

        float speed = (width * (RING_FINAL_WIDTH_MULTIPLIER - 1)) / 5;
        long duration = (long) (((width / RING_THICKNESS_DIVIDER) / speed) * 1000);
        animationDuration = (int) (duration * 5);

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

        disposableInterval = observable.subscribe(success -> System.out.println("Disposable interval: " + success),
                Throwable::printStackTrace);

        searchText.setText("");
    }


}