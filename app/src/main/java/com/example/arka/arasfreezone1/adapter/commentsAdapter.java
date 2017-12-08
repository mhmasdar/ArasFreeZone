package com.example.arka.arasfreezone1.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.arka.arasfreezone1.R;
import com.example.arka.arasfreezone1.commentsActivity;
import com.like.LikeButton;

/**
 * Created by mohamadHasan on 27/11/2017.
 */

public class commentsAdapter extends RecyclerView.Adapter<commentsAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private RecyclerView answerRecycler;
    private LinearLayout lytReplyPreview;
    private ImageView btnCloseReply;

    public commentsAdapter(Context context, LinearLayout lytReplyPreview, ImageView btnCloseReply) {
        this.context = context;
        this.lytReplyPreview = lytReplyPreview;
        this.btnCloseReply = btnCloseReply;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_comments_list, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        btnCloseReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lytReplyPreview.setVisibility(View.GONE);
            }
        });

        holder.lytReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lytReplyPreview.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }


    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private TextView txtCommentBody;
        private LinearLayout lytReply;
        private LinearLayout lytCommentHeight;
        private LikeButton btnLike;
        private TextView txtLikeCount;

        myViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtCommentBody = (TextView) itemView.findViewById(R.id.txtCommentBody);
            lytReply = (LinearLayout) itemView.findViewById(R.id.lytReply);
            answerRecycler = (RecyclerView) itemView.findViewById(R.id.answerRecycler);
            lytCommentHeight = (LinearLayout) itemView.findViewById(R.id.lytCommentHeight);
            btnLike = (LikeButton) itemView.findViewById(R.id.btnLike);
            txtLikeCount = (TextView) itemView.findViewById(R.id.txtLikeCount);

            setUpRecyclerView();
        }
    }

    private void setUpRecyclerView() {

        commentsAnswerAdapter adapter = new commentsAnswerAdapter(context);
        answerRecycler.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(context);
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        answerRecycler.setLayoutManager(mLinearLayoutManagerVertical);
    }

}
