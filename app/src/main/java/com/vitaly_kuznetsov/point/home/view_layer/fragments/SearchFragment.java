package com.vitaly_kuznetsov.point.home.view_layer.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.base_models.custom_views.PointMainButton;
import com.vitaly_kuznetsov.point.home.view_layer.interfaces.BasicHomeFragment;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

public class SearchFragment extends Fragment implements BasicHomeFragment {

    private ConstraintLayout fragmentLayout;
    private View ringView;
    private ArrayList<ImageView> imageViewArrayList;
    private float width = 0;
    private int animationDuration;
    private ViewGroup.LayoutParams layoutParams;

    private Context context;

    private static final float RING_FINAL_WIDTH_MULTIPLIER = 2.411F;
    private static final float RING_THICKNESS_DIVIDER = 4;

    private Disposable disposableInterval;

    //--------------Lifecycle Actions----------------

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        init(view);
        return view;
    }
    //--------------Initialize Fragment upon creation----------------

    @Override
    public void init(View view) {
        PointMainButton pointMainButton = view.findViewById(R.id.point_main_button);

        ringView = view.findViewById(R.id.ring_view);
        layoutParams = ringView.getLayoutParams();

        fragmentLayout = view.findViewById(R.id.constraint_layout_0);
        imageViewArrayList = new ArrayList<>();
        pointMainButton.setOnCheckedChangeListener(checkedChangeListener());

        context = view.getContext();
    }

    private CompoundButton.OnCheckedChangeListener checkedChangeListener(){
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked){
                    recursiveAnimation();
                    Log.i("Animation Info: ", "Checked");
                }
                else {
                    Log.i("Animation Info: ", "NOTChecked");
                    disposableInterval.dispose();
                    for (int i = 0; i < 5; i++){
                        fragmentLayout.removeView(imageViewArrayList.get(i));
                        imageViewArrayList.get(i).clearAnimation();
                    }
                    imageViewArrayList.clear();
                }
            }
        };
    }

    private void recursiveAnimation(){

        if (width == 0){
            width = ringView.getWidth() * PointMainButton.SCALE;
            width /= 2;
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

        Predicate<Long> isLessThanFive = new Predicate<Long>() {
            @Override
            public boolean test(Long seconds) throws Exception{
                return seconds < 5;
            }
        };

        final Consumer<Long> createRing = new Consumer<Long>() {
            @Override
            public void accept(Long attempt) throws Exception {
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
            }
        };

        Observable<Long> observable = Observable.interval(0, duration, TimeUnit.MILLISECONDS)
                .takeWhile(isLessThanFive).doOnNext(createRing);

        disposableInterval = observable.subscribe();
    }
}