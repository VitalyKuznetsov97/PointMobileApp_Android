package com.vitaly_kuznetsov.point.search.view_layer;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BasicFragmentInterface;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BasicStateActionsFragment;
import com.vitaly_kuznetsov.point.base_models.reusable_fragments.UserCardFragment;
import com.vitaly_kuznetsov.point.home.view_layer.activities.HomeActivity;
import com.vitaly_kuznetsov.point.search.presenter_layer.MatchFragmentPresenter;

import java.util.Objects;

public class MatchFragment extends Fragment implements BasicFragmentInterface, BasicStateActionsFragment,
            MatchFragmentInterface{

    private MatchFragmentPresenter presenter;

    private ConstraintLayout userFrameLayout;

    @Override
    public void attachPresenter(MatchFragmentPresenter matchFragmentPresenter) {
        presenter = matchFragmentPresenter;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match, container, false);
        init(view);
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onCancelButtonClicked();
        presenter.detachView();
    }

    @Override
    public void init(View view) {
        presenter.attachView(this);

        view.findViewById(R.id.button_decline).setOnClickListener(view1 -> presenter.onDeclineButtonClicked());
        view.findViewById(R.id.button_accept).setOnClickListener(view1 -> presenter.onAcceptButtonClicked());
        view.findViewById(R.id.cancel_pic).setOnClickListener(view1 -> presenter.onCancelButtonClicked());
        userFrameLayout = view.findViewById(R.id.constraint_layout_1);

        setViewsFromUserDataModel();
    }

    @Override
    public boolean isReadyToProgress() {
        return true;
    }

    @Override
    public void setViewsFromUserDataModel() {
        userFrameLayout.setBackgroundResource(presenter.getUserDataModel().getMyGender() == 1 ?
                R.drawable.match_male_frame : R.drawable.match_female_frame);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
        fragmentTransaction.add(R.id.constraint_layout_user_card_matched, new UserCardFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void startButtonAnimation() {
        ImageView declineButton = Objects.requireNonNull(getActivity()).findViewById(R.id.button_decline);
        declineButton.setOnClickListener(null);
        declineButton.setVisibility(View.INVISIBLE);
        Objects.requireNonNull(getActivity()).findViewById(R.id.divider_0).setVisibility(View.INVISIBLE);

        int translationXBy = Objects.requireNonNull(getActivity()).findViewById(R.id.constraint_layout_1).getWidth();
        ImageView button = Objects.requireNonNull(getActivity()).findViewById(R.id.button_accept);
        button.setOnClickListener(view -> presenter.onButtonAfterAnimationClicked());
        button.animate().translationXBy(-(translationXBy / 4)).setDuration(1000).start();
    }

    @Override
    public void saveFragmentState() { }

    public MatchFragmentPresenter getPresenter() {
        return presenter;
    }

    @Override
    public HomeActivity getHomeActivity() {
        return (HomeActivity) getActivity();
    }
}
