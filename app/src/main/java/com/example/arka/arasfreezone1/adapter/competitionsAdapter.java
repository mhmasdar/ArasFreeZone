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
 * Created by mohamadHasan on 02/12/2017.
 */

public class competitionsAdapter extends RecyclerView.Adapter<competitionsAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;



    public competitionsAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_competitions_list, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    private void initView() {

    }


    class myViewHolder extends RecyclerView.ViewHolder {


        private TextView txtQuestion;
        private LinearLayout lytOption1;
        private TextView txtOption1;
        private RadioButton radioOption1;
        private LinearLayout lytOption2;
        private TextView txtOption2;
        private RadioButton radioOption2;
        private LinearLayout lytOption3;
        private TextView txtOption3;
        private RadioButton radioOption3;
        private LinearLayout lytOption4;
        private TextView txtOption4;
        private RadioButton radioOption4;

        myViewHolder(View itemView) {
            super(itemView);
            txtQuestion = (TextView) itemView.findViewById(R.id.txtQuestion);
            lytOption1 = (LinearLayout) itemView.findViewById(R.id.lytOption1);
            txtOption1 = (TextView) itemView.findViewById(R.id.txtOption1);
            radioOption1 = (RadioButton) itemView.findViewById(R.id.radioOption1);
            lytOption2 = (LinearLayout) itemView.findViewById(R.id.lytOption2);
            txtOption2 = (TextView) itemView.findViewById(R.id.txtOption2);
            radioOption2 = (RadioButton) itemView.findViewById(R.id.radioOption2);
            lytOption3 = (LinearLayout) itemView.findViewById(R.id.lytOption3);
            txtOption3 = (TextView) itemView.findViewById(R.id.txtOption3);
            radioOption3 = (RadioButton) itemView.findViewById(R.id.radioOption3);
            lytOption4 = (LinearLayout) itemView.findViewById(R.id.lytOption4);
            txtOption4 = (TextView) itemView.findViewById(R.id.txtOption4);
            radioOption4 = (RadioButton) itemView.findViewById(R.id.radioOption4);
        }
    }


}