package com.example.arka.arasfreezone1.fragments;


import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.arka.arasfreezone1.R;
import com.example.arka.arasfreezone1.adapter.newsListAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class newsListFragment extends Fragment {


    private RelativeLayout lytSearch;
    private RelativeLayout lytMenu;
    private TabLayout newsTabLayout;
    private RecyclerView rc;
    private Typeface typeface;
    private LinearLayout lytMain;
    private LinearLayout lytEmpty;
    private LinearLayout lytDisconnect;

    public newsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        initView(view);

        typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/font.ttf");

        newsTabLayout.addTab(newsTabLayout.newTab().setText("هنری"));
        newsTabLayout.addTab(newsTabLayout.newTab().setText("اجتماعی"));
        newsTabLayout.addTab(newsTabLayout.newTab().setText("فرهنگی"));
        newsTabLayout.addTab(newsTabLayout.newTab().setText("سیاسی"));
        newsTabLayout.addTab(newsTabLayout.newTab().setText("همه اخبار"));


//        referendimViewPager adapter = new referendimViewPager (getActivity().getSupportFragmentManager());
//        newsPager.setAdapter(adapter);
//        newsPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(newsTabLayout));


        newsTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                newsPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        View root = newsTabLayout.getChildAt(0);
        if (root instanceof LinearLayout) {
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(getResources().getColor(R.color.colorPrimary));
            drawable.setSize(2, 1);
            ((LinearLayout) root).setDividerPadding(10);
            ((LinearLayout) root).setDividerDrawable(drawable);
        }

        changeTabsFont();

        setUpRecyclerView();

        boolean handler = new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                newsTabLayout.getTabAt(4).select();
            }
        }, 2);
        return view;
    }

    private void initView(View view) {
        lytSearch = (RelativeLayout) view.findViewById(R.id.lytSearch);
        lytMenu = (RelativeLayout) view.findViewById(R.id.lytMenu);
        newsTabLayout = (TabLayout) view.findViewById(R.id.newsTabLayout);
        rc = (RecyclerView) view.findViewById(R.id.rc);
        lytMain = (LinearLayout) view.findViewById(R.id.lytMain);
        lytEmpty = (LinearLayout) view.findViewById(R.id.lytEmpty);
        lytDisconnect = (LinearLayout) view.findViewById(R.id.lytDisconnect);
    }

    private void changeTabsFont() {

        ViewGroup vg = (ViewGroup) newsTabLayout.getChildAt(0);
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

    private void setUpRecyclerView() {

        newsListAdapter adapter = new newsListAdapter(getContext());
        rc.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        rc.setLayoutManager(mLinearLayoutManagerVertical);
    }
}
