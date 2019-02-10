package com.vitaly_kuznetsov.point.base_models.custom_views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;

public class CheckableImageView extends android.support.v7.widget.AppCompatImageView
        implements Checkable {

    private boolean checked;

    private static final int[] CHECKED_STATE_SET = { android.R.attr.state_checked };

    public CheckableImageView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int[] onCreateDrawableState(final int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked())
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        return drawableState;
    }

    @Override
    public void toggle() {
        if (!isChecked())
            setChecked(true);
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void setChecked(final boolean checked) {
        if (this.checked == checked)
            return;
        this.checked = checked;
        refreshDrawableState();
    }

    @Override
    public void setOnClickListener(final OnClickListener listener) {
        View.OnClickListener onClickListener = v -> {
            toggle();
            listener.onClick(v);
        };
        super.setOnClickListener(onClickListener);
    }
}
