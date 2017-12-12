package com.example.arka.arasfreezone1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.arka.arasfreezone1.R;

/**
 * Created by mohamadHasan on 12/12/2017.
 */

public class facilityDialogAdapter extends RecyclerView.Adapter<facilityDialogAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;



    public facilityDialogAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public facilityDialogAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_competitions_list, parent, false);
        facilityDialogAdapter.myViewHolder holder = new facilityDialogAdapter.myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(facilityDialogAdapter.myViewHolder holder, int position) {
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