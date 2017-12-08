package com.example.arka.arasfreezone1;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class favoriteActivity extends AppCompatActivity {

    private RelativeLayout lytSearch;
    private RelativeLayout lytBack;
    private LinearLayout lytMain;
    private RecyclerView recycle;
    private LinearLayout lytQuestionSend;
    private LinearLayout lytEmpty;
    private LinearLayout lytDisconnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        initView();


        lytBack.setOnClickListener(new View.OnClickListener() {
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
        lytSearch = (RelativeLayout) findViewById(R.id.lytSearch);
        lytBack = (RelativeLayout) findViewById(R.id.lytBack);
        lytMain = (LinearLayout) findViewById(R.id.lytMain);
        recycle = (RecyclerView) findViewById(R.id.recycle);
        lytQuestionSend = (LinearLayout) findViewById(R.id.lytQuestionSend);
        lytEmpty = (LinearLayout) findViewById(R.id.lytEmpty);
        lytDisconnect = (LinearLayout) findViewById(R.id.lytDisconnect);
    }
}
