package com.example.arka.arasfreezone1.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.arka.arasfreezone1.MainActivity;
import com.example.arka.arasfreezone1.R;
import com.example.arka.arasfreezone1.fragments.detailsFragment;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mohamadHasan on 30/11/2017.
 */

public class restaurantListAdapter extends RecyclerView.Adapter<restaurantListAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;


    private int currentPage = 0;
    private int totalSlides = 3;
    private ViewPager mPager;
    private boolean slider = false;
    private static Timer swipeTimer = new Timer();


    public restaurantListAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_restaurant_list, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        if (position == 0)
        {
            mPager.setVisibility(View.VISIBLE);
        }

        holder.rating.setRating(Float.parseFloat("2.0"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailsFragment fragment = new detailsFragment();
                MainActivity activity = (MainActivity) context;
                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container2, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return 7;
    }


    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private TextView txtRank;
        private TextView txtAddress;
        private RatingBar rating;
        private ImageView imgNews;


        myViewHolder(View itemView) {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtRank = (TextView) itemView.findViewById(R.id.txtRank);
            txtAddress = (TextView) itemView.findViewById(R.id.txtAddress);
            rating = (RatingBar) itemView.findViewById(R.id.rating);
            imgNews = (ImageView) itemView.findViewById(R.id.imgNews);
            mPager = (ViewPager) itemView.findViewById(R.id.pager);
            Drawable drawable = rating.getProgressDrawable();
            drawable.setColorFilter(Color.parseColor("#16a086"), PorterDuff.Mode.SRC_ATOP);

            initSlider();
        }
    }

    private void initSlider() {


        mPager.setAdapter(new categoriesSliderAdapter(context));


        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == totalSlides) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2000, 4000);

    }
}