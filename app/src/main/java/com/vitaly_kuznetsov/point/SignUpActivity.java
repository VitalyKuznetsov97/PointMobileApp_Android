package com.vitaly_kuznetsov.point;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity implements SignUpOneFragment.OnFragmentInteractionListener{

    private static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ImageView goBackIcon = findViewById(R.id.go_back_icon);
        goBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        final ImageButton indicatorZero = findViewById(R.id.indicator_0);
        final ImageButton indicatorOne = findViewById(R.id.indicator_1);
        final ImageButton indicatorTwo = findViewById(R.id.indicator_2);
        Button button = findViewById(R.id.sign_up_button_1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        TextView logInTextView = findViewById(R.id.already_have_text_view);
        logInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFragmentInteraction();
            }
        });
    }

    @Override
    public void onFragmentInteraction() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        SignUpOneFragment fragment = SignUpOneFragment.newInstance("hallow", "zabl");
        fragmentTransaction.add(R.id.constraint_layout_2, fragment);
        fragmentTransaction.commit();
    }
}
