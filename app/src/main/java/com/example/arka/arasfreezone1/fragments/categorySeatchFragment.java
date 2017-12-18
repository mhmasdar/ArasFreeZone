package com.example.arka.arasfreezone1.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.arka.arasfreezone1.R;

import net.cachapa.expandablelayout.ExpandableLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class categorySeatchFragment extends Fragment {


    private ExpandableLayout expandableLayout;
    private LinearLayout lytSearch;
    private LinearLayout lytSeperate;
    private EditText edtSearch;
    private RelativeLayout lytBack;
    private LinearLayout lytSort;
    private LinearLayout lytFilter;
    private Dialog dialog;


    public categorySeatchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_seatch, container, false);
        initView(view);


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                expandableLayout.expand();

                Animation fade = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
                Animation fade1 = AnimationUtils.loadAnimation(getContext(), R.anim.search_btn_back);


                lytSort.setVisibility(View.VISIBLE);
                lytFilter.setVisibility(View.VISIBLE);
                lytBack.setVisibility(View.VISIBLE);
                lytSeperate.setVisibility(View.VISIBLE);


                lytSort.startAnimation(fade);
                lytFilter.startAnimation(fade);
                lytBack.startAnimation(fade1);
                lytSeperate.startAnimation(fade);


            }
        }, 200);


        lytSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSortDialog();
            }
        });


        lytFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFiltersDialog();
            }
        });


        lytSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        lytBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });

        return view;
    }

    private void initView(View view) {
        expandableLayout = (ExpandableLayout) view.findViewById(R.id.expandable_layout);
        lytSearch = (LinearLayout) view.findViewById(R.id.lytSearch);
        edtSearch = (EditText) view.findViewById(R.id.edt_search);
        lytBack = (RelativeLayout) view.findViewById(R.id.lytBack);
        lytSort = (LinearLayout) view.findViewById(R.id.lytSort);
        lytFilter = (LinearLayout) view.findViewById(R.id.lytFilter);
        lytSeperate = (LinearLayout) view.findViewById(R.id.lytSeperate);
    }


    private void showFiltersDialog() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_filters);
        Button btnFilter = (Button) dialog.findViewById(R.id.btnFilter);
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private void showSortDialog() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sort);
        Button btnSort = (Button) dialog.findViewById(R.id.btnSort);
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
}
