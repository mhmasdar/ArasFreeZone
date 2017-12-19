package com.example.arka.arasfreezone1.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.arka.arasfreezone1.MainActivity;
import com.example.arka.arasfreezone1.R;
import com.example.arka.arasfreezone1.fragments.detailsFragment;
import com.example.arka.arasfreezone1.models.PlacesModel;

import java.util.List;

/**
 * Created by mohamadHasan on 19/12/2017.
 */

public class driversAdapter extends RecyclerView.Adapter<driversAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;


    public driversAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public driversAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_drivers_list, parent, false);
        driversAdapter.myViewHolder holder = new driversAdapter.myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(driversAdapter.myViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }


    class myViewHolder extends RecyclerView.ViewHolder {

        myViewHolder(View itemView) {
            super(itemView);

        }
    }
}