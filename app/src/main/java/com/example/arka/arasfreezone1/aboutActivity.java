package com.example.arka.arasfreezone1;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uncopt.android.widget.text.justify.JustifiedTextView;

public class aboutActivity extends AppCompatActivity {

    private RelativeLayout relativeBack;
    private ImageView imgArka;
    private String url = "http://arkatech.ir";
    private LinearLayout lytCall;
    private TextView txtWebSite;
    private JustifiedTextView txt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/font.ttf");
        txt1.setTypeface(typeface);

        imgArka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });


        txtWebSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });


        lytCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCall = new Intent(Intent.ACTION_DIAL);
                intentCall.setData(Uri.fromParts("tel", "04133303866", null));
                startActivity(intentCall);
            }
        });


        relativeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.activity_back_enter);
    }

    private void initView() {
        relativeBack = (RelativeLayout) findViewById(R.id.relativeBack);
        imgArka = (ImageView) findViewById(R.id.imgArka);
        lytCall = (LinearLayout) findViewById(R.id.lytCall);
        txtWebSite = (TextView) findViewById(R.id.txtWebSite);
        txt1 = (JustifiedTextView) findViewById(R.id.txt1);
    }
}
