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
import com.example.arka.arasfreezone1.fragments.detailsOfficeFragment;
import com.example.arka.arasfreezone1.models.PlacesModel;

import java.util.List;

/**
 * Created by mohamadHasan on 12/12/2017.
 */

public class officeListAdapter  extends RecyclerView.Adapter<officeListAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<PlacesModel> placesList;
    private String tblName;

    public officeListAdapter(Context context, List<PlacesModel> placesList, String tblName) {
        this.context = context;
        this.placesList = placesList;
        mInflater = LayoutInflater.from(context);
        this.tblName = tblName;
    }

    @Override
    public officeListAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_office_list, parent, false);
        officeListAdapter.myViewHolder holder = new officeListAdapter.myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(officeListAdapter.myViewHolder holder, int position) {

        final PlacesModel currentObj = placesList.get(position);
        holder.setData(currentObj, position);

        //holder.rating.setRating(Float.parseFloat("2.0"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailsOfficeFragment fragment = new detailsOfficeFragment();

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
        private TextView txtAddress;
        private ImageView imgNews;

        int position;
        public PlacesModel current;


        myViewHolder(View itemView) {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtAddress = (TextView) itemView.findViewById(R.id.txtAddress);
            imgNews = (ImageView) itemView.findViewById(R.id.imgNews);


        }

        public void setData(PlacesModel current, int position) {

            this.txtName.setText(current.name);
            this.txtAddress.setText(current.address);
            //this.imgNews.setImageResource();

            this.position = position;
            this.current = current;

        }


    }


}
