package com.example.arka.arasfreezone1.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arka.arasfreezone1.MainActivity;
import com.example.arka.arasfreezone1.R;
import com.example.arka.arasfreezone1.fragments.detailsOfficeFragment;
import com.example.arka.arasfreezone1.models.PlacesModel;

import java.util.List;

/**
 * Created by mohamadHasan on 22/12/2017.
 */

public class organizationAdapter  extends RecyclerView.Adapter<organizationAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;


    public organizationAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public organizationAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_organization_list, parent, false);
        organizationAdapter.myViewHolder holder = new organizationAdapter.myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(organizationAdapter.myViewHolder holder, int position) {

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
