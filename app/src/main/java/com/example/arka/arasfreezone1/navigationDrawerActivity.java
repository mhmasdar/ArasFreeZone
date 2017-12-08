package com.example.arka.arasfreezone1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class navigationDrawerActivity extends AppCompatActivity {

    private TextView txtLogin;
    private LinearLayout lytIntroduction;
    private LinearLayout lytFavorites;
    private LinearLayout lytreferendum;
    private LinearLayout lytSuggestion;
    private LinearLayout lytShare;
    private LinearLayout lytAbout;
    private LinearLayout lytExit;
    private LinearLayout lytUserInformation;
    private TextView txtUserName;
    private TextView txtUserEmail;
    private ImageView UserImage;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        initView();


        lytSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), suggestionActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.activity_enter, R.anim.stay);
            }
        });


        lytreferendum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), referendumActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.activity_enter, R.anim.stay);
            }
        });


        lytIntroduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), introduceActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.activity_enter, R.anim.stay);
            }
        });


        lytFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), favoriteActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.activity_enter, R.anim.stay);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.top_to_bottom);
    }

    private void initView() {
        txtLogin = (TextView) findViewById(R.id.txtLogin);
        lytIntroduction = (LinearLayout) findViewById(R.id.lytIntroduction);
        lytFavorites = (LinearLayout) findViewById(R.id.lytFavorites);
        lytreferendum = (LinearLayout) findViewById(R.id.lytreferendum);
        lytSuggestion = (LinearLayout) findViewById(R.id.lytSuggestion);
        lytShare = (LinearLayout) findViewById(R.id.lytShare);
        lytAbout = (LinearLayout) findViewById(R.id.lytAbout);
        lytExit = (LinearLayout) findViewById(R.id.lytExit);
        lytUserInformation = (LinearLayout) findViewById(R.id.lytUserInformation);
        txtUserName = (TextView) findViewById(R.id.txtUserName);
        txtUserEmail = (TextView) findViewById(R.id.txtUserEmail);
        UserImage = (ImageView) findViewById(R.id.UserImage);
        imgBack = (ImageView) findViewById(R.id.imgBack);
    }
}
