package com.example.arka.arasfreezone1.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import com.example.arka.arasfreezone1.models.PlacesModel;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mohamadHasan on 30/11/2017.
 */

public class restaurantListAdapter extends RecyclerView.Adapter<restaurantListAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<PlacesModel> placesList;
    private String tblName;

    public restaurantListAdapter(Context context, List<PlacesModel> placesList, String tblName) {
        this.context = context;
        this.placesList = placesList;
        mInflater = LayoutInflater.from(context);
        this.tblName = tblName;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_restaurant_list, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        final PlacesModel currentObj = placesList.get(position);
        holder.setData(currentObj, position);

        //holder.rating.setRating(Float.parseFloat("2.0"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailsFragment fragment = new detailsFragment();

                Bundle args = new Bundle();
                args.putInt("ID", currentObj.id);
                args.putString("TBL_NAME", tblName);
                fragment.setArguments(args);

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
        return placesList.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private TextView txtRank;
        private TextView txtAddress;
        private RatingBar rating;
        private ImageView imgNews;

        int position;
        public PlacesModel current;


        myViewHolder(View itemView) {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtRank = (TextView) itemView.findViewById(R.id.txtRank);
            txtAddress = (TextView) itemView.findViewById(R.id.txtAddress);
            rating = (RatingBar) itemView.findViewById(R.id.rating);
            imgNews = (ImageView) itemView.findViewById(R.id.imgNews);
            Drawable drawable = rating.getProgressDrawable();
            drawable.setColorFilter(Color.parseColor("#16a086"), PorterDuff.Mode.SRC_ATOP);


        }

        private void setData(PlacesModel current, int position) {

            this.rating.setRating(Float.parseFloat(current.star + ""));
            this.txtName.setText(current.name);
            this.txtAddress.setText(current.address);
            //this.imgNews.setImageResource();
            this.txtRank.setText(current.star + "");

            this.position = position;
            this.current = current;

        }


    }


}