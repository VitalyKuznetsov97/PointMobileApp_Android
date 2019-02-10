package com.vitaly_kuznetsov.point.base_models.reusable_fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BaseContract;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BasicFragmentInterface;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BasicModelActionsInterface;
import com.vitaly_kuznetsov.point.base_models.mvp_base_contract.BasicStateActionsFragment;
import com.vitaly_kuznetsov.point.base_models.user_data_model.model.UserDataModel;
import com.vitaly_kuznetsov.point.home.presenter_layer.HomeViewPresenter;
import com.vitaly_kuznetsov.point.home.view_layer.activities.HomeActivity;
import com.vitaly_kuznetsov.point.search.view_layer.MatchFragment;
import com.vitaly_kuznetsov.point.settings.view_layer.SettingsActivity;

import java.util.Objects;

public class UserCardFragment extends Fragment implements BasicFragmentInterface, BasicStateActionsFragment {

    private UserDataModel userDataModel;
    private ConstraintLayout frameLayout;
    private ImageView userAvatar;
    private TextView userNameTextView;
    private TextView userBioEditText;
    private TextView phoneNumberTextView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int layoutId;

        switch (container.getId()){
            case R.id.constraint_layout_user_card : layoutId = R.layout.fragment_user_card; break;
            case R.id.constraint_layout_user_card_matched : layoutId = R.layout.fragment_user_card_matched; break;
            default: layoutId = R.layout.fragment_user_card_horizontal; break;
        }

        View view = inflater.inflate(layoutId, container, false);
        init(view);
        return view;
    }

    //--------------Initialize Fragment upon creation----------------

    @Override
    public void init(View view) {

        frameLayout = view.findViewById(R.id.constraint_layout_picture);
        userAvatar = view.findViewById(R.id.user_avatar);
        userNameTextView = view.findViewById(R.id.text_name);
        userBioEditText = view.findViewById(R.id.bio_edit_text);

        if (getActivity() instanceof HomeActivity) {
            Fragment currentActivityFragment = ((HomeActivity) Objects.requireNonNull(getActivity())).getCurrentFragment();

            if (currentActivityFragment instanceof MatchFragment) {
                MatchFragment fragment = (MatchFragment) currentActivityFragment;
                this.userDataModel = Objects.requireNonNull(fragment).getPresenter().getUserDataModel();
            } else {
                BaseContract.Presenter presenter = ((BaseContract.View) Objects.requireNonNull(getActivity())).getPresenter();
                this.userDataModel = ((HomeViewPresenter) presenter).getUserDataModel();
                phoneNumberTextView = view.findViewById(R.id.text_phone);
            }
        }
        else if(getActivity() instanceof SettingsActivity) {
            BaseContract.Presenter presenter = ((BaseContract.View) Objects.requireNonNull(getActivity())).getPresenter();
            this.userDataModel = ((BasicModelActionsInterface) presenter).getUserDataModel();
            phoneNumberTextView = view.findViewById(R.id.text_phone);
        }

        setViewsFromUserDataModel();
    }

    @Override
    public void setViewsFromUserDataModel(){

        if (userDataModel.getMyGender() == 1) {
            frameLayout.setBackgroundResource(R.drawable.male_frame);
            userAvatar.setImageResource(R.drawable.male_background_pic);
        }
        else {
            frameLayout.setBackgroundResource(R.drawable.female_frame);
            userAvatar.setImageResource(R.drawable.female_background_pic);
        }

        userNameTextView.setText(userDataModel.getNickname());
        userBioEditText.setText(userDataModel.getMyBio());
        if (phoneNumberTextView != null) phoneNumberTextView.setText(userDataModel.getPhone());
    }

    //--------------Save Fragment data, if Fragment is being--------------
    // -------------in onStop State or starts http request----------------

    @Override
    public void saveFragmentState() {
        userDataModel.setMyBio(String.valueOf(userBioEditText.getText()));
    }

    //--------------Checks if all info in fragment is filled correctly----------------

    @Override
    public boolean isReadyToProgress() {
        return !String.valueOf(userBioEditText.getText()).equals("");
    }

}
