package com.example.arka.arasfreezone1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.example.arka.arasfreezone1.R;


/**
 * Created by mohamadHasan on 23/12/2017.
 */

public class referendumAdapter extends RecyclerView.Adapter<referendumAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;



    public referendumAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_referendum_list, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(myViewHolder holder, final int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }


    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtQuestion;
        private LinearLayout lytRefList;
        private TextView txtOption1;
        private TextView txtOption2;
        private TextView txtOption3;
        private TextView txtOption4;
        private RadioGroup radioGroup;
        private RadioButton radio1;
        private RadioButton radio2;
        private RadioButton radio3;
        private RadioButton radio4;
        private LinearLayout lytRefResults;
        private TextView txtPercent;
        private ProgressBar progress;

        myViewHolder(View itemView) {
            super(itemView);

            txtQuestion = (TextView) itemView.findViewById(R.id.txtQuestion);
            lytRefList = (LinearLayout) itemView.findViewById(R.id.lytRefList);
            txtOption1 = (TextView) itemView.findViewById(R.id.txtOption1);
            txtOption2 = (TextView) itemView.findViewById(R.id.txtOption2);
            txtOption3 = (TextView) itemView.findViewById(R.id.txtOption3);
            txtOption4 = (TextView) itemView.findViewById(R.id.txtOption4);
            radioGroup = (RadioGroup) itemView.findViewById(R.id.radioGroup);
            radio1 = (RadioButton) itemView.findViewById(R.id.radio1);
            radio2 = (RadioButton) itemView.findViewById(R.id.radio2);
            radio3 = (RadioButton) itemView.findViewById(R.id.radio3);
            radio4 = (RadioButton) itemView.findViewById(R.id.radio4);
            lytRefResults = (LinearLayout) itemView.findViewById(R.id.lytRefResults);
            txtPercent = (TextView) itemView.findViewById(R.id.txtPercent);
            progress = (ProgressBar) itemView.findViewById(R.id.progress);

            //progress.setProgress(65);
            progress.setRotation(180);
        }

    }
}