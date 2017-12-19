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
import com.example.arka.arasfreezone1.app;
import com.example.arka.arasfreezone1.commentsActivity;
import com.example.arka.arasfreezone1.models.CommentModel;
import com.like.LikeButton;

import java.util.List;

/**
 * Created by mohamadHasan on 27/11/2017.
 */

public class commentsAdapter extends RecyclerView.Adapter<commentsAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private RecyclerView answerRecycler;
    private LinearLayout lytReplyPreview;
    private ImageView btnCloseReply;
    private TextView txtReplyName, txtReplyBody;
    private List<CommentModel> commentList;

    public static int idAnswer = -1;

    public commentsAdapter(Context context, LinearLayout lytReplyPreview, ImageView btnCloseReply, TextView txtReplyBody, TextView txtReplyName, List<CommentModel> commentList) {
        this.context = context;
        this.lytReplyPreview = lytReplyPreview;
        this.btnCloseReply = btnCloseReply;
        this.txtReplyBody = txtReplyBody;
        this.txtReplyName = txtReplyName;
        this.commentList = commentList;
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

        final CommentModel currentObj = commentList.get(position);
        holder.setData(currentObj, position);

        btnCloseReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lytReplyPreview.setVisibility(View.GONE);
                idAnswer = -1;
            }
        });

        holder.lytReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lytReplyPreview.setVisibility(View.VISIBLE);
                txtReplyName.setText(currentObj.name);
                txtReplyBody.setText(currentObj.body);
                idAnswer = currentObj.id;
            }
        });

        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName, txtDate;
        private TextView txtCommentBody;
        private LinearLayout lytReply;
        private LinearLayout lytCommentHeight;
        private LikeButton btnLike;
        private TextView txtLikeCount;

        int position;
        public CommentModel current;

        myViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtCommentBody = (TextView) itemView.findViewById(R.id.txtCommentBody);
            lytReply = (LinearLayout) itemView.findViewById(R.id.lytReply);
            answerRecycler = (RecyclerView) itemView.findViewById(R.id.answerRecycler);
            lytCommentHeight = (LinearLayout) itemView.findViewById(R.id.lytCommentHeight);
            btnLike = (LikeButton) itemView.findViewById(R.id.btnLike);
            txtLikeCount = (TextView) itemView.findViewById(R.id.txtLikeCount);
            txtDate = itemView.findViewById(R.id.txtDate);

        }

        private void setData(CommentModel current, int position) {

            this.txtName.setText(current.name);
            this.txtCommentBody.setText(current.body);
            this.txtLikeCount.setText(current.likeCount + "");
            txtDate.setText(app.changeDateToString(current.date));

            if (current.answers.size() > 0)
                setUpRecyclerView(current.answers);

            this.position = position;
            this.current = current;

        }

    }

    private void setUpRecyclerView(List<CommentModel> answerList) {

        commentsAnswerAdapter adapter = new commentsAnswerAdapter(context, answerList);
        answerRecycler.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(context);
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        answerRecycler.setLayoutManager(mLinearLayoutManagerVertical);
    }

}
