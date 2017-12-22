package com.example.arka.arasfreezone1.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.arka.arasfreezone1.R;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class referendumFragment extends Fragment {


    private SmoothProgressBar lytLoading;
    private LinearLayout lytMain;
    private RecyclerView recycle;
    private LinearLayout lytQuestionSend;
    private LinearLayout lytEmpty;
    private LinearLayout lytDisconnect;

    public referendumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_referendum, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        lytLoading = (SmoothProgressBar) view.findViewById(R.id.lytLoading);
        lytMain = (LinearLayout) view.findViewById(R.id.lytMain);
        recycle = (RecyclerView) view.findViewById(R.id.recycle);
        lytQuestionSend = (LinearLayout) view.findViewById(R.id.lytQuestionSend);
        lytEmpty = (LinearLayout) view.findViewById(R.id.lytEmpty);
        lytDisconnect = (LinearLayout) view.findViewById(R.id.lytDisconnect);
    }
}
