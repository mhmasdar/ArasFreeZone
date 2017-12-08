package com.example.arka.arasfreezone1.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.arka.arasfreezone1.R;
import com.example.arka.arasfreezone1.commentsActivity;
import com.like.LikeButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class newsDetailsFragment extends Fragment {


    private RelativeLayout relativeBack;
    private TextView txtNewsTitle;
    private ImageView imgShare;
    private LikeButton imgLike;
    private TextView txtDate;
    private ImageView imgNews;
    private TextView txtNewsBody;
    private LinearLayout lytNewsComments;

    public newsDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_details, container, false);
        initView(view);

        lytNewsComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), commentsActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.fragment_enter, R.anim.fragment_exit);
            }
        });


        relativeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });


        return view;
    }

    private void initView(View view) {
        relativeBack = (RelativeLayout) view.findViewById(R.id.relativeBack);
        txtNewsTitle = (TextView) view.findViewById(R.id.txtNewsTitle);
        imgShare = (ImageView) view.findViewById(R.id.imgShare);
        imgLike = (LikeButton) view.findViewById(R.id.btnLike);
        txtDate = (TextView) view.findViewById(R.id.txtDate);
        imgNews = (ImageView) view.findViewById(R.id.imgNews);
        txtNewsBody = (TextView) view.findViewById(R.id.txtNewsBody);
        lytNewsComments = (LinearLayout) view.findViewById(R.id.lytNewsComments);
    }
}
