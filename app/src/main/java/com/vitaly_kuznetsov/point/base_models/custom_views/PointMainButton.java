package com.vitaly_kuznetsov.point.base_models.custom_views;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ToggleButton;

public class PointMainButton extends ToggleButton implements Animation.AnimationListener {

    private RotateAnimation rotate;
    private int fromStartedDegree;
    private int degree;
    private CountDownTimer timer;

    private static final long LONG_DURATION = 3600;
    private static final long SHORT_DURATION = 720;
    private long currentDuration;
    public static final float SCALE = 0.84F;

    public PointMainButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        degree = 0;
    }

    private void startRotationAnimation(long duration){

        this.clearAnimation();
        if (timer != null) timer.cancel();
        if (rotate != null) rotate.cancel();
        if (degree >= 360) degree %= 360;
        fromStartedDegree = degree;
        currentDuration = duration;

        rotate = new RotateAnimation(degree, degree + 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(duration);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setRepeatCount(Animation.INFINITE);
        rotate.setRepeatMode(Animation.RESTART);
        rotate.setAnimationListener(this);

        this.startAnimation(rotate);

        final long countDownInterval = duration / 360;

        timer = new CountDownTimer(duration, countDownInterval) {

            @Override public void onTick(long l) { degree = fromStartedDegree + 360 - (int) (l / countDownInterval); }

            @Override public void onFinish() { /* Empty */ }
        };

        timer.start();
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();

        if (this.isChecked()) {
            this.setScaleX(SCALE);
            this.setScaleY(SCALE);
        } else {
            this.setScaleX(1);
            this.setScaleY(1);
            startRotationAnimation(LONG_DURATION);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventAction = event.getAction();

        int radius = this.getWidth() / 2;
        int x = (int) event.getX() - radius;
        int y = (int) event.getY() - radius;

        if (this.isChecked() && (Math.pow(x, 2) + Math.pow(y, 2) < Math.pow(radius, 2))
                && eventAction == MotionEvent.ACTION_UP){
                performClick();
        }
        else if (!this.isChecked() && (Math.pow(x, 2) + Math.pow(y, 2) < Math.pow(radius, 2))) {
            if (eventAction == MotionEvent.ACTION_UP) performClick();
            if (currentDuration != SHORT_DURATION) startRotationAnimation(SHORT_DURATION);
        }
        else if (!this.isChecked()) startRotationAnimation(LONG_DURATION);

        invalidate();
        return true;
    }

    @Override
    public boolean performClick() {
        this.clearAnimation();
        if (rotate != null)
            rotate.cancel();
        return super.performClick();
    }

    @Override
    public void onAnimationStart(Animation animation) {}

    @Override
    public void onAnimationEnd(Animation animation) {}

    @Override
    public void onAnimationRepeat(Animation animation) {
        if (animation.getClass() == RotateAnimation.class) {
            timer.cancel();
            timer.start();
        }
    }

}