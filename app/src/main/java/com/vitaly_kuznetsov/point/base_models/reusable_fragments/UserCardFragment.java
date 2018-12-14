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
import com.vitaly_kuznetsov.point.home.view_layer.activities.HomeActivity;

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

        int layoutId = container.getId() == R.id.constraint_layout_user_card ?
                R.layout.fragment_user_card : R.layout.fragment_user_card_horizontal;

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
        phoneNumberTextView = view.findViewById(R.id.text_phone);

        if (getActivity() instanceof BaseContract.View) {
            BaseContract.Presenter presenter = ((BaseContract.View) getActivity()).getPresenter();
            this.userDataModel = ((BasicModelActionsInterface) presenter).getUserDataModel();
        }
        else {
            HomeActivity homeActivity = (HomeActivity) getHost();
            this.userDataModel = Objects.requireNonNull(homeActivity).getUserDataModel();
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
        phoneNumberTextView.setText(userDataModel.getPhone());
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
