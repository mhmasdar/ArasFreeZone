package com.example.arka.arasfreezone1.adapter;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.arka.arasfreezone1.MainActivity;
import com.example.arka.arasfreezone1.R;
import com.example.arka.arasfreezone1.fragments.eventsDetailsFragment;
import com.example.arka.arasfreezone1.fragments.newsDetailsFragment;

/**
 * Created by mohamadHasan on 13/12/2017.
 */

public class eventsListAdapter extends RecyclerView.Adapter<eventsListAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;



    public eventsListAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public eventsListAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_events_list, parent, false);
        eventsListAdapter.myViewHolder holder = new eventsListAdapter.myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(eventsListAdapter.myViewHolder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventsDetailsFragment fragment = new eventsDetailsFragment();
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
        return 5;
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        myViewHolder(View itemView) {
            super(itemView);

        }
    }
}
