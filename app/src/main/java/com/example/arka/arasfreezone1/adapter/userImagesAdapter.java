package com.example.arka.arasfreezone1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.arka.arasfreezone1.R;


/**
 * Created by Mohamad Hasan on 2/16/2018.
 */

public class userImagesAdapter extends RecyclerView.Adapter<userImagesAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;


    public userImagesAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public userImagesAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_user_images_list, parent, false);
        userImagesAdapter.myViewHolder holder = new userImagesAdapter.myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(userImagesAdapter.myViewHolder holder, int position) {
        if (position % 2 == 0)
            holder.img.setImageResource(R.drawable.cul1);
    }

    @Override
    public int getItemCount() {
        return 12;
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;

        myViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
        }
    }
}