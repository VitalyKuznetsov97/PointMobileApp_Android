package com.vitaly_kuznetsov.point.view_layer.authentication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.view_layer.authentication.fragments.*;

public class SignUpActivity extends AppCompatActivity{

    private static int count;
    private static FragmentManager fragmentManager;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        count = 1;

        ImageView goBackIcon = findViewById(R.id.go_back_icon);
        goBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment = new SignUpOneFragment();
        fragmentTransaction.add(R.id.fragment_constraint_layout, fragment);
        fragmentTransaction.commit();

        final ImageButton indicatorZero = findViewById(R.id.indicator_0);
        final ImageButton indicatorOne = findViewById(R.id.indicator_1);
        final ImageButton indicatorTwo = findViewById(R.id.indicator_2);
        nextButton = findViewById(R.id.next_button);

        indicatorZero.setActivated(true);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment();
                if (count == 0){
                    indicatorZero.setActivated(!indicatorZero.isActivated());
                }
                else if (count == 1){
                    indicatorOne.setActivated(!indicatorOne.isActivated());
                }
                else if (count == 2){
                    indicatorTwo.setActivated(!indicatorTwo.isActivated());
                    count = -1;
                }
                count++;
            }
        });
    }

    private void changeFragment(){
        Fragment newFragment;

        switch (count){
            case 0:
                newFragment = new SignUpOneFragment();
                break;
            case 1:
                newFragment = new SignUpTwoFragment();
                break;
            case 2:
                newFragment = new GetCodeFragment();
                nextButton.setVisibility(View.GONE);
                break;
            default:
                newFragment = null;
                break;
        }

        if (newFragment != null) {
            // Create new fragment and transaction
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack
            transaction.replace(R.id.fragment_constraint_layout, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }
}
