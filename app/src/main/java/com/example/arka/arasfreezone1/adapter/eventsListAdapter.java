package com.example.arka.arasfreezone1.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.arka.arasfreezone1.MainActivity;
import com.example.arka.arasfreezone1.R;
import com.example.arka.arasfreezone1.app;
import com.example.arka.arasfreezone1.fragments.eventsDetailsFragment;
import com.example.arka.arasfreezone1.fragments.newsDetailsFragment;
import com.example.arka.arasfreezone1.models.EventModel;

import java.util.List;

/**
 * Created by mohamadHasan on 13/12/2017.
 */

public class eventsListAdapter extends RecyclerView.Adapter<eventsListAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<EventModel> eventList;



    public eventsListAdapter(Context context, List<EventModel> eventList) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.eventList = eventList;
    }

    @Override
    public eventsListAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_events_list, parent, false);
        eventsListAdapter.myViewHolder holder = new eventsListAdapter.myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(eventsListAdapter.myViewHolder holder, int position) {

        final EventModel currentObj = eventList.get(position);
        holder.setData(currentObj, position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventsDetailsFragment fragment = new eventsDetailsFragment();

                Bundle args = new Bundle();
                args.putInt("id", currentObj.id);
                args.putString("body", currentObj.body);
                args.putString("title", currentObj.title);
                args.putString("startTime", currentObj.startTime);
                args.putInt("startDate", currentObj.startDate);
                args.putString("endTime", currentObj.endTime);
                args.putInt("endDate", currentObj.endDate);
                args.putDouble("lat", currentObj.lat);
                args.putString("place", currentObj.place);
                args.putDouble("lon", currentObj.lon);
                args.putString("address", currentObj.address);
                args.putString("phone", currentObj.phone);
                args.putString("website", currentObj.website);
                args.putBoolean("visibility", currentObj.visibility);
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
        return eventList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txvTitle;
        private TextView txtDate;
        private TextView txtAddress;
        private ImageView imgTitle;

        int position;
        private EventModel current;

        myViewHolder(View itemView) {
            super(itemView);

            txvTitle = (TextView) itemView.findViewById(R.id.txvTitle);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            txtAddress = (TextView) itemView.findViewById(R.id.txtAddress);
            imgTitle = (ImageView) itemView.findViewById(R.id.imgTitle);

        }

        private void setData(EventModel current, int position) {

            this.txvTitle.setText(current.title);



            this.txtDate.setText("زمان: " + app.changeDateToString(current.startDate));
            this.txtAddress.setText("مکان: " + current.address);
            //this.imgNews.setImageResource();

            this.position = position;
            this.current = current;

        }

    }
}
