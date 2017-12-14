package com.example.arka.arasfreezone1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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


    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtQuestion;
        private TextView txtOption1;
        private TextView txtOption2;
        private TextView txtOption3;
        private TextView txtOption4;
        private RadioGroup radioGroup;
        private RadioButton radio1;
        private RadioButton radio2;
        private RadioButton radio3;
        private RadioButton radio4;

        myViewHolder(View itemView) {
            super(itemView);

            txtQuestion = (TextView) itemView.findViewById(R.id.txtQuestion);
            txtOption1 = (TextView) itemView.findViewById(R.id.txtOption1);
            txtOption2 = (TextView) itemView.findViewById(R.id.txtOption2);
            txtOption3 = (TextView) itemView.findViewById(R.id.txtOption3);
            txtOption4 = (TextView) itemView.findViewById(R.id.txtOption4);
            radioGroup = (RadioGroup) itemView.findViewById(R.id.radioGroup);
            radio1 = (RadioButton) itemView.findViewById(R.id.radio1);
            radio2 = (RadioButton) itemView.findViewById(R.id.radio2);
            radio3 = (RadioButton) itemView.findViewById(R.id.radio3);
            radio4 = (RadioButton) itemView.findViewById(R.id.radio4);
        }
    }


}