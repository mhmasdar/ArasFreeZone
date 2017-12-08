package com.example.arka.arasfreezone1.fragments.categories;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.arka.arasfreezone1.R;
import com.example.arka.arasfreezone1.adapter.organizationSliderAdapter;
import com.example.arka.arasfreezone1.app;
import com.viewpagerindicator.CirclePageIndicator;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class organizationFragment extends Fragment {


    private ViewPager mPager;
    private int currentPage = 0;
    private RelativeLayout organizationSlider;
    private CirclePageIndicator indicator;
    private LinearLayout lytOrgIntroduce;
    private LinearLayout lytOrgAddress;
    private LinearLayout lytOrgPhone;
    private LinearLayout lytOrgWeb;
    private ExpandableLayout expanableLayout1, expanableLayout2, expanableLayout3, expanableLayout4;
    private boolean openCheck1 = false, openCheck2 = false, openCheck3 = false, openCheck4 = false;
    private int height;
    private boolean OrganiztionSlider = false;
    private ViewPager pager;
    private TextView txtOrgintroduce;
    private TextView txtOrgAddress;
    private TextView txtOrgPhone;
    private TextView txtOrgWeb;
    private static Timer swipeTimer = new Timer();

    public organizationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_organization, container, false);
        initView(view);
        initSlider(view);

        lytOrgIntroduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!openCheck1) {
                    height = lytOrgIntroduce.getHeight(); // get height of layout
                    setLayoutsHeight();
                    expanableLayout1.expand();

                    openCheck1 = true;
                } else {
                    expanableLayout1.setExpanded(false);
                    openCheck1 = false;
                }
            }
        });

        lytOrgAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!openCheck2) {
                    height = lytOrgAddress.getHeight(); // get height of layout
                    setLayoutsHeight();
                    expanableLayout2.expand();

                    openCheck2 = true;
                } else {
                    expanableLayout2.setExpanded(false);
                    openCheck2 = false;
                }
            }
        });


        lytOrgPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!openCheck3) {
                    height = lytOrgPhone.getHeight(); // get height of layout
                    setLayoutsHeight();
                    expanableLayout3.expand();

                    openCheck3 = true;
                } else {
                    expanableLayout3.setExpanded(false);
                    openCheck3 = false;
                }
            }
        });

        lytOrgWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!openCheck4) {
                    height = lytOrgWeb.getHeight(); // get height of layout
                    setLayoutsHeight();
                    expanableLayout4.expand();

                    openCheck4 = true;
                } else {
                    expanableLayout4.setExpanded(false);
                    openCheck4 = false;
                }
            }
        });


        return view;
    }


    private void initSlider(View view) {

        mPager = (ViewPager) view.findViewById(R.id.pager);


        mPager.setAdapter(new organizationSliderAdapter(getContext()));


        CirclePageIndicator indicator = (CirclePageIndicator)
                view.findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

        //Set circle indicator radius
        indicator.setRadius(5 * density);


        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 3) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        if (OrganiztionSlider == false) {
            swipeTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 2000, 4000);
            OrganiztionSlider = true;
        }
        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }

    private void initView(View view) {
        organizationSlider = (RelativeLayout) view.findViewById(R.id.organizationSlider);
        indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
        lytOrgIntroduce = (LinearLayout) view.findViewById(R.id.lytOrgIntroduce);
        lytOrgAddress = (LinearLayout) view.findViewById(R.id.lytOrgAddress);
        lytOrgPhone = (LinearLayout) view.findViewById(R.id.lytOrgPhone);
        lytOrgWeb = (LinearLayout) view.findViewById(R.id.lytOrgWeb);
        expanableLayout1 = (ExpandableLayout) view.findViewById(R.id.expanableLayout1);
        expanableLayout2 = (ExpandableLayout) view.findViewById(R.id.expanableLayout2);
        expanableLayout3 = (ExpandableLayout) view.findViewById(R.id.expanableLayout3);
        expanableLayout4 = (ExpandableLayout) view.findViewById(R.id.expanableLayout4);
        pager = (ViewPager) view.findViewById(R.id.pager);
        txtOrgintroduce = (TextView) view.findViewById(R.id.txtOrgintroduce);
        txtOrgAddress = (TextView) view.findViewById(R.id.txtOrgAddress);
        txtOrgPhone = (TextView) view.findViewById(R.id.txtOrgPhone);
        txtOrgWeb = (TextView) view.findViewById(R.id.txtOrgWeb);
    }

    private void setLayoutsHeight() {
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
        p.weight = 0;
        lytOrgAddress.setLayoutParams(p);
        lytOrgIntroduce.setLayoutParams(p);
        lytOrgPhone.setLayoutParams(p);
        lytOrgWeb.setLayoutParams(p);
    }
}
