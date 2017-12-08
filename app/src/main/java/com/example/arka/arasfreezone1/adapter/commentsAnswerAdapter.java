package com.example.arka.arasfreezone1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.arka.arasfreezone1.R;

/**
 * Created by mohamadHasan on 30/11/2017.
 */

public class commentsAnswerAdapter extends RecyclerView.Adapter<commentsAnswerAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;


    public commentsAnswerAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public commentsAnswerAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_comments_answer_list, parent, false);
        commentsAnswerAdapter.myViewHolder holder = new commentsAnswerAdapter.myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(commentsAnswerAdapter.myViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 2;
    }



    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private TextView txtCommentBody;


        myViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtCommentBody = (TextView) itemView.findViewById(R.id.txtCommentBody);
        }
    }

}