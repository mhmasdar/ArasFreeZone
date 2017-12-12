package com.example.arka.arasfreezone1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arka.arasfreezone1.R;

/**
 * Created by mohamadHasan on 12/12/2017.
 */

public class menuDialogAdapter extends RecyclerView.Adapter<menuDialogAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;



    public menuDialogAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public menuDialogAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_menu_list, parent, false);
        menuDialogAdapter.myViewHolder holder = new menuDialogAdapter.myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(menuDialogAdapter.myViewHolder holder, int position) {
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
