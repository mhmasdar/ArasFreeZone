package com.example.arka.arasfreezone1.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arka.arasfreezone1.R;
import com.example.arka.arasfreezone1.app;


/**
 * A simple {@link Fragment} subclass.
 */
public class supportFragment extends Fragment {


    public supportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_support, container, false);
        app.check = 3;
        return view;
    }

}
