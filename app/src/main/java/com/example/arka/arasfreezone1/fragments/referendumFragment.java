package com.example.arka.arasfreezone1.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.arka.arasfreezone1.R;
import com.example.arka.arasfreezone1.adapter.competitionsAdapter;
import com.example.arka.arasfreezone1.adapter.referendumAdapter;
import com.example.arka.arasfreezone1.models.ReferendumModel;

import java.util.List;

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
    private ProgressBar progress1;
    private TextView txtCompetitionTitle;
    private TextView txtSend;
    private LinearLayout lytRepetitive;

    public referendumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_referendum, container, false);
        initView(view);


        setUpRecyclerView();

        return view;
    }

    private void initView(View view) {
        lytLoading = (SmoothProgressBar) view.findViewById(R.id.lytLoading);
        lytMain = (LinearLayout) view.findViewById(R.id.lytMain);
        recycle = (RecyclerView) view.findViewById(R.id.recycle);
        lytQuestionSend = (LinearLayout) view.findViewById(R.id.lytQuestionSend);
        lytEmpty = (LinearLayout) view.findViewById(R.id.lytEmpty);
        lytDisconnect = (LinearLayout) view.findViewById(R.id.lytDisconnect);
        progress1 = (ProgressBar) view.findViewById(R.id.progress);
        txtCompetitionTitle = (TextView) view.findViewById(R.id.txtCompetitionTitle);
        txtSend = (TextView) view.findViewById(R.id.txtSend);
        lytRepetitive = (LinearLayout) view.findViewById(R.id.lytRepetitive);
    }


    private void setUpRecyclerView() {

        referendumAdapter adapter = new referendumAdapter(getContext());
        recycle.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recycle.setLayoutManager(mLinearLayoutManagerVertical);
    }

}
