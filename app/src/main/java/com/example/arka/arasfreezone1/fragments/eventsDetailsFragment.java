package com.example.arka.arasfreezone1.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arka.arasfreezone1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class eventsDetailsFragment extends Fragment {


    public eventsDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events_details, container, false);
        return view;
    }

}
