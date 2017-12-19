package com.example.arka.arasfreezone1.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arka.arasfreezone1.MainActivity;
import com.example.arka.arasfreezone1.R;
import com.example.arka.arasfreezone1.app;
import com.example.arka.arasfreezone1.fragments.newsDetailsFragment;
import com.example.arka.arasfreezone1.models.NewsModel;
import com.like.LikeButton;

import java.util.List;

/**
 * Created by mohamadHasan on 23/11/2017.
 */

public class newsListAdapter extends RecyclerView.Adapter<newsListAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private int lastPosition = -1;
    private List<NewsModel> newsList;

    public newsListAdapter(Context context, List<NewsModel> newsList) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.newsList = newsList;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_news_list, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        setAnimation(holder.itemView, position);

        final NewsModel currentObj = newsList.get(position);
        holder.setData(currentObj, position);

        holder.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newsDetailsFragment fragment = new newsDetailsFragment();

                Bundle args = new Bundle();
                args.putInt("ID", currentObj.id);
                args.putInt("Type", currentObj.Type);
                args.putInt("likeCount", currentObj.likeCount);
                args.putInt("Date", currentObj.Date);
                args.putString("Img", currentObj.Img);
                args.putString("Body", currentObj.Body);
                args.putString("Title", currentObj.Title);
                fragment.setArguments(args);

                MainActivity activity = (MainActivity) context;
                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container3, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtNewsTitle;
        private TextView txtNewsBody;
        private ImageView imgNews;
        private ImageView imgShare;
        private LikeButton imgLike;
        private TextView txtCommentCount;
        private TextView txtDate;
        private TextView txtLikeCount;

        int position;
        public NewsModel current;

        myViewHolder(View itemView) {
            super(itemView);
            imgShare = (ImageView) itemView.findViewById(R.id.imgShare);
            imgLike = (LikeButton) itemView.findViewById(R.id.btnLike);
            txtCommentCount = (TextView) itemView.findViewById(R.id.txtCommentCount);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            txtNewsTitle = (TextView) itemView.findViewById(R.id.txtNewsTitle);
            txtNewsBody = (TextView) itemView.findViewById(R.id.txtNewsBody);
            txtLikeCount = (TextView) itemView.findViewById(R.id.txtLikeCount);
            imgNews = (ImageView) itemView.findViewById(R.id.imgNews);
        }

        private void setData(NewsModel current, int position) {

            this.txtNewsTitle.setText(current.Title);
            this.txtNewsBody.setText(current.Body);
            this.txtLikeCount.setText(current.likeCount + "");
            txtDate.setText(app.changeDateToString(current.Date));


            this.position = position;
            this.current = current;

        }

    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

}
