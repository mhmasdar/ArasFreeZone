package com.example.arka.arasfreezone1;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class imageActivity extends Activity {

    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        initView();


        Glide.with(this).load(R.drawable.back1).diskCacheStrategy(DiskCacheStrategy.NONE).into(back);

    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
    }
}
