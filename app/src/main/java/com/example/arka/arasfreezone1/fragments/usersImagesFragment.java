package com.example.arka.arasfreezone1.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arka.arasfreezone1.R;
import com.example.arka.arasfreezone1.adapter.eventsListAdapter;
import com.example.arka.arasfreezone1.adapter.userImagesAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class usersImagesFragment extends Fragment {


    private RecyclerView recycler;
    private FloatingActionButton floactAction;

    public usersImagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users_images, container, false);
        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        floactAction = (FloatingActionButton) view.findViewById(R.id.floactAction);

        setUpRecyclerView();


        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.FOCUS_DOWN){
                    floactAction.hide();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy >0) {
                    // Scroll Down
                    if (floactAction.isShown()) {
                        floactAction.hide();
                    }
                }
                else if (dy <0) {
                    // Scroll Up
                    if (!floactAction.isShown()) {
                        floactAction.show();
                    }
                }
            }
        });


        return view;
    }

    private void setUpRecyclerView() {

        userImagesAdapter adapter = new userImagesAdapter(getContext());
        recycler.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recycler.setLayoutManager(gridLayoutManager);
    }

}
