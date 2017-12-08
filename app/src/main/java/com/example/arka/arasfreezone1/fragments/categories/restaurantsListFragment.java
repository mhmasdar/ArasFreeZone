package com.example.arka.arasfreezone1.fragments.categories;


import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.arka.arasfreezone1.R;
import com.example.arka.arasfreezone1.adapter.restaurantListAdapter;
import com.example.arka.arasfreezone1.adapter.categoriesSliderAdapter;

import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class restaurantsListFragment extends Fragment {


    private RelativeLayout relativeBack;
    private TabLayout catListTabLayout;
    private RecyclerView recycler;


    private Typeface typeface;


    public restaurantsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurants_list, container, false);
        initView(view);


        typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/font.ttf");


        setUpRecyclerView();


        relativeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });

        catListTabLayout.addTab(catListTabLayout.newTab().setText("بستنی"));
        catListTabLayout.addTab(catListTabLayout.newTab().setText("شیرینی و آجیل"));
        catListTabLayout.addTab(catListTabLayout.newTab().setText("کافی شاپ"));
        catListTabLayout.addTab(catListTabLayout.newTab().setText("فست فود"));
        catListTabLayout.addTab(catListTabLayout.newTab().setText("رستوران"));

        catListTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        View root = catListTabLayout.getChildAt(0);
        if (root instanceof LinearLayout) {
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(getResources().getColor(R.color.colorPrimary));
            drawable.setSize(2, 1);
            ((LinearLayout) root).setDividerPadding(10);
            ((LinearLayout) root).setDividerDrawable(drawable);
        }

        changeTabsFont();

        boolean handler = new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                catListTabLayout.getTabAt(4).select();
            }
        }, 2);

        return view;
    }

    private void changeTabsFont() {

        ViewGroup vg = (ViewGroup) catListTabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {

                    ((TextView) tabViewChild).setTypeface(typeface);
                }
            }
        }

    }

    private void initView(View view) {
        relativeBack = (RelativeLayout) view.findViewById(R.id.relative_back);
        catListTabLayout = (TabLayout) view.findViewById(R.id.catListTabLayout);
        recycler = (RecyclerView) view.findViewById(R.id.recycler);
    }

    private void setUpRecyclerView(){

        restaurantListAdapter adapter = new restaurantListAdapter(getContext());
        recycler.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(mLinearLayoutManagerVertical);
    }


}
