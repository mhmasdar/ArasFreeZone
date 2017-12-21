package com.example.arka.arasfreezone1;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arka.arasfreezone1.fragments.categoryFragment;
import com.example.arka.arasfreezone1.fragments.newsListFragment;
import com.example.arka.arasfreezone1.fragments.supportFragment;

public class MainActivity extends AppCompatActivity {


    private LinearLayout container, container2, container3, container4;
    private LinearLayout lytSupport;
    private ImageView imgSupport;
    private TextView txtSupport;
    private LinearLayout lytEvents;
    private ImageView imgEvents;
    private TextView txtEvents;
    private LinearLayout lytMap;
    private ImageView imgMap;
    private TextView txtMap;
    private LinearLayout lytCategory;
    private ImageView imgCategory;
    private TextView txtCategory;
    private LinearLayout lytHome;
    private ImageView imgHome;
    private TextView txtHome;
    private FragmentTransaction ft, ft2, ft3, ft4 , ft5;
    private int check=0; // check for which fragment is show
    private boolean frgCreateCheck2 = false, frgCreateCheck3 = false, frgCreateCheck4 = false; // این متغیر ها برای تشخیص اینکه اولین بار بر روی هر یک از آیتم های پایینی کلیک شده است ، تعریف شده اند
    // اولین بار که روی آیتم های پایینی کلیک می شود، فرگمنت ایجاد و مقداردهی شده و نمایش داده می شود. در دفعات بعدی هنگام کلیک بر روی آیتم های پایین، فقط فرگمنت ها visible و gone  می شوند

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


        //display home fragment as default
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.container, new HomeFragment());
        ft.commit();


        lytCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (frgCreateCheck2 == false)
                {
                    ft2 = getSupportFragmentManager().beginTransaction();
                    ft2.add(R.id.container2 , new categoryFragment());
                    ft2.commit();
                    frgCreateCheck2=true;

                    setLytCategory();
                }

                else {
                    if (check != 1) {
                        setLytCategory();
                    }
                }
            }
        });


        lytEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (frgCreateCheck3 == false){
                    ft3 = getSupportFragmentManager().beginTransaction();
                    ft3.add(R.id.container3 , new newsListFragment());
                    ft3.commit();
                    frgCreateCheck3=true;

                    setLytEvants();
                }

                else {
                    if (check != 2) {
                        setLytEvants();
                    }
                }
            }
        });


        lytSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (frgCreateCheck4 == false){
                    ft4 = getSupportFragmentManager().beginTransaction();
                    ft4.add(R.id.container4 , new supportFragment());
                    ft4.commit();
                    frgCreateCheck4=true;

                    setLytSupport();
                }

                else {
                    if (check != 3) {
                        setLytSupport();
                    }
                }
            }
        });


        lytHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check != 0) {
                    setLytHome();
                }
            }
        });


        lytMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mapIntent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(mapIntent);

            }
        });

    }

    private void initView() {
        container = (LinearLayout) findViewById(R.id.container);
        container2 = (LinearLayout) findViewById(R.id.container2);
        container3 = (LinearLayout) findViewById(R.id.container3);
        container4 = (LinearLayout) findViewById(R.id.container4);
        lytSupport = (LinearLayout) findViewById(R.id.lytSupport);
        imgSupport = (ImageView) findViewById(R.id.imgSupport);
        txtSupport = (TextView) findViewById(R.id.txtSupport);
        lytEvents = (LinearLayout) findViewById(R.id.lytEvents);
        imgEvents = (ImageView) findViewById(R.id.imgEvents);
        txtEvents = (TextView) findViewById(R.id.txtEvents);
        lytMap = (LinearLayout) findViewById(R.id.lytMap);
        imgMap = (ImageView) findViewById(R.id.imgMap);
        txtMap = (TextView) findViewById(R.id.txtMap);
        lytCategory = (LinearLayout) findViewById(R.id.lytCategory);
        imgCategory = (ImageView) findViewById(R.id.imgCategory);
        txtCategory = (TextView) findViewById(R.id.txtCategory);
        lytHome = (LinearLayout) findViewById(R.id.lytHome);
        imgHome = (ImageView) findViewById(R.id.imgHome);
        txtHome = (TextView) findViewById(R.id.txtHome);
    }

    private void setLytCategory(){
        imgCategory.setImageResource(R.drawable.ic_category_selected);
        imgHome.setImageResource(R.drawable.ic_home);
        imgEvents.setImageResource(R.drawable.ic_event);
        imgSupport.setImageResource(R.drawable.ic_support);
        txtCategory.setTextColor(getResources().getColor(R.color.colorPrimary));
        txtHome.setTextColor(getResources().getColor(R.color.mainBarText));
        txtEvents.setTextColor(getResources().getColor(R.color.mainBarText));
        txtSupport.setTextColor(getResources().getColor(R.color.mainBarText));


        container.setVisibility(View.GONE);
        container3.setVisibility(View.GONE);
        container4.setVisibility(View.GONE);
        container2.setVisibility(View.VISIBLE);

        check=1;
        app.check = 1;
    }

    private void setLytEvants(){
        imgCategory.setImageResource(R.drawable.ic_category);
        imgHome.setImageResource(R.drawable.ic_home);
        imgEvents.setImageResource(R.drawable.ic_event_selected);
        imgSupport.setImageResource(R.drawable.ic_support);
        txtCategory.setTextColor(getResources().getColor(R.color.mainBarText));
        txtHome.setTextColor(getResources().getColor(R.color.mainBarText));
        txtEvents.setTextColor(getResources().getColor(R.color.colorPrimary));
        txtSupport.setTextColor(getResources().getColor(R.color.mainBarText));


        container.setVisibility(View.GONE);
        container3.setVisibility(View.VISIBLE);
        container4.setVisibility(View.GONE);
        container2.setVisibility(View.GONE);

        check=2;
        app.check = 2;
    }

    private void setLytSupport(){
        imgCategory.setImageResource(R.drawable.ic_category);
        imgHome.setImageResource(R.drawable.ic_home);
        imgEvents.setImageResource(R.drawable.ic_event);
        imgSupport.setImageResource(R.drawable.ic_support_selected);
        txtCategory.setTextColor(getResources().getColor(R.color.mainBarText));
        txtHome.setTextColor(getResources().getColor(R.color.mainBarText));
        txtEvents.setTextColor(getResources().getColor(R.color.mainBarText));
        txtSupport.setTextColor(getResources().getColor(R.color.colorPrimary));


        container.setVisibility(View.GONE);
        container3.setVisibility(View.GONE);
        container4.setVisibility(View.VISIBLE);
        container2.setVisibility(View.GONE);

        check=3;
        app.check = 3;
    }

    private void setLytHome(){
        imgCategory.setImageResource(R.drawable.ic_category);
        imgHome.setImageResource(R.drawable.ic_home_selected);
        imgEvents.setImageResource(R.drawable.ic_event);
        imgSupport.setImageResource(R.drawable.ic_support);
        txtCategory.setTextColor(getResources().getColor(R.color.mainBarText));
        txtHome.setTextColor(getResources().getColor(R.color.colorPrimary));
        txtEvents.setTextColor(getResources().getColor(R.color.mainBarText));
        txtSupport.setTextColor(getResources().getColor(R.color.mainBarText));


        container.setVisibility(View.VISIBLE);
        container3.setVisibility(View.GONE);
        container4.setVisibility(View.GONE);
        container2.setVisibility(View.GONE);

        check=0;
        app.check = 0;
    }


    @Override
    public void onBackPressed() {

        if (app.check == 0){
            if (doubleBackToExitPressedOnce) {
                finish();
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "برای خروج مجددا کلید برگشت را بزنید", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }

        else if (app.check == 1 || app.check == 2 || app.check == 3)
            setLytHome();

        else
            super.onBackPressed();
    }
}
