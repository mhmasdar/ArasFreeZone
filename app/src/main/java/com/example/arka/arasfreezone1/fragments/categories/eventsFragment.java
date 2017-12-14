package com.example.arka.arasfreezone1.fragments.categories;


import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.arka.arasfreezone1.R;
import com.example.arka.arasfreezone1.adapter.categoriesSliderAdapter;
import com.example.arka.arasfreezone1.adapter.eventsListAdapter;
import com.example.arka.arasfreezone1.adapter.officeListAdapter;
import com.example.arka.arasfreezone1.app;
import com.example.arka.arasfreezone1.models.PlacesModel;

import java.util.List;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class eventsFragment extends Fragment {


    private RelativeLayout relativeBack;
    private RecyclerView recycler;


    public eventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_events, container, false);
        initView(view);

        setUpRecyclerView();

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
        relativeBack = (RelativeLayout) view.findViewById(R.id.relative_back);
        recycler = (RecyclerView) view.findViewById(R.id.rc);
    }

    private void setUpRecyclerView(){

        eventsListAdapter adapter = new eventsListAdapter(getContext());
        recycler.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(mLinearLayoutManagerVertical);
    }


}
