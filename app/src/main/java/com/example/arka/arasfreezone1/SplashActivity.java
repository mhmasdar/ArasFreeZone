package com.example.arka.arasfreezone1;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.arka.arasfreezone1.services.WebService;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    private ImageView imgSp1, imgSp2, imgSp3;
    private LinearLayout splashBack;
    private ImageView imgAras;
    private TextView txtSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();

        Animation sp0 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash0);
        Animation sp1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash1);
        Animation sp2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash2);
        Animation logo = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_logo);
        Animation text = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_text);
        splashBack.startAnimation(sp0);
        imgSp1.startAnimation(sp2);
        imgSp3.startAnimation(sp2);
        imgSp2.startAnimation(sp1);
        imgAras.startAnimation(logo);
        txtSplash.startAnimation(text);

        setUpTimer();


    }

    // timer of splash
    public void setUpTimer() {
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                WebServiceCallBack callBack = new WebServiceCallBack();
                callBack.execute();

            }
        }, SPLASH_TIME_OUT);
    }

    private void initView() {
        imgSp1 = (ImageView) findViewById(R.id.imgSp1);
        imgSp2 = (ImageView) findViewById(R.id.imgSp2);
        imgSp3 = (ImageView) findViewById(R.id.imgSp3);
        splashBack = (LinearLayout) findViewById(R.id.splashBack);
        imgAras = (ImageView) findViewById(R.id.imgAras);
        txtSplash = (TextView) findViewById(R.id.txtSplash);
    }

    private class WebServiceCallBack extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        int result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.getCulture(app.isInternetOn());

            result = webService.getOffices(app.isInternetOn());

            result = webService.getEatings(app.isInternetOn());

            result = webService.getMedicals(app.isInternetOn());

            result = webService.getServices(app.isInternetOn());

            result = webService.getShoppings(app.isInternetOn());

            result = webService.getTourisms(app.isInternetOn());

            result = webService.getTransports(app.isInternetOn());

            result = webService.getRests(app.isInternetOn());

            result = webService.getEvents(app.isInternetOn());

            result = webService.getImages(app.isInternetOn());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


//            SharedPreferences prefs = getApplicationContext().getSharedPreferences("MYPREFS", 0);
//            Intent i;
//
//
//            if (prefs.getBoolean("LogIn_Check", false))
//            {
//                i = new Intent(getApplicationContext(), MainActivity.class);
//            }
//            else
//            {
//                i = new Intent(getApplicationContext(), loginActivity.class);
//            }


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(imgAras, "App_Logo");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this, pairs);
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i, options.toBundle());
                finish();
            }
            else
            {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
//        finish();
    }

    @Override
    public void onBackPressed() {

    }
}
