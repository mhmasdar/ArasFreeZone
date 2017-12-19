package com.example.arka.arasfreezone1.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.arka.arasfreezone1.R;
import com.example.arka.arasfreezone1.adapter.driversAdapter;
import com.example.arka.arasfreezone1.adapter.eventsListAdapter;
import com.example.arka.arasfreezone1.models.EventModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class driversFragment extends Fragment {


    private RelativeLayout relativeBack;
    private LinearLayout lytMain;
    private RecyclerView recycler;
    private LinearLayout lytEmpty;
    private LinearLayout lytDisconnect;

    public driversFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_drivers, container, false);
        initView(view);

        setUpRecyclerView();

        return view;
    }

    private void initView(View view) {
        relativeBack = (RelativeLayout) view.findViewById(R.id.relative_back);
        lytMain = (LinearLayout) view.findViewById(R.id.lytMain);
        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        lytEmpty = (LinearLayout) view.findViewById(R.id.lytEmpty);
        lytDisconnect = (LinearLayout) view.findViewById(R.id.lytDisconnect);
    }


    private void setUpRecyclerView() {

        driversAdapter adapter = new driversAdapter(getContext());
        recycler.setAdapter(adapter);
        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(mLinearLayoutManagerVertical);
    }

}
