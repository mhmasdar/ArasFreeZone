package com.example.arka.arasfreezone1.fragments.categories;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.arka.arasfreezone1.R;
import com.example.arka.arasfreezone1.adapter.eventsListAdapter;
import com.example.arka.arasfreezone1.db.DatabaseHelper;
import com.example.arka.arasfreezone1.models.EventModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class eventsFragment extends Fragment {


    private RelativeLayout relativeBack;
    private RecyclerView recycler;
    private LinearLayout lytMain, lytEmpty, lytDisconnect;

    private List<EventModel> eventList;

    public eventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        initView(view);

//        if (app.isInternetOn()) {
//            WebServiceCallBack webServiceCallBack = new WebServiceCallBack();
//            webServiceCallBack.execute();
//        } else {
//            lytMain.setVisibility(View.GONE);
//            lytEmpty.setVisibility(View.GONE);
//            lytDisconnect.setVisibility(View.VISIBLE);
//        }

        //setUpRecyclerView();

        DatabaseCallback databaseCallback = new DatabaseCallback(getContext(), "Tbl_Events");
        databaseCallback.execute();

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
        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        lytMain = view.findViewById(R.id.lytMain);
        lytDisconnect = view.findViewById(R.id.lytDisconnect);
        lytEmpty = view.findViewById(R.id.lytEmpty);
    }

    private void setUpRecyclerView(List<EventModel> list) {

        eventsListAdapter adapter = new eventsListAdapter(getContext(), list);
        recycler.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recycler.setLayoutManager(gridLayoutManager);
    }

//    private class WebServiceCallBack extends AsyncTask<Object, Void, Void> {
//
//        private WebService webService;
//
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            eventList = new ArrayList<>();
//            webService = new WebService();
//        }
//
//        @Override
//        protected Void doInBackground(Object... params) {
//
//            //eventList = webService.getEvents(app.isInternetOn(), app.getDate());
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//
//            if (eventList != null) {
//
//                if (eventList.size() > 0) {
//                    lytMain.setVisibility(View.VISIBLE);
//                    lytDisconnect.setVisibility(View.GONE);
//                    lytEmpty.setVisibility(View.GONE);
//                    setUpRecyclerView(eventList);
//                } else {
//                    lytMain.setVisibility(View.GONE);
//                    lytDisconnect.setVisibility(View.GONE);
//                    lytEmpty.setVisibility(View.VISIBLE);
//                }
//
//            } else {
//                lytMain.setVisibility(View.GONE);
//                lytEmpty.setVisibility(View.GONE);
//                lytDisconnect.setVisibility(View.VISIBLE);
//            }
//
//        }
//
//    }

    public class DatabaseCallback extends AsyncTask<Object, Void, Void> {


        private DatabaseHelper databaseHelper;
        List<EventModel> eventList;
        private Context context;
        private String tblName;

        public DatabaseCallback(Context context, String tblName) {
            this.context = context;
            this.tblName = tblName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            eventList = new ArrayList<>();
            databaseHelper = new DatabaseHelper(context);
        }


        @Override
        protected Void doInBackground(Object... objects) {

            eventList = databaseHelper.selectAllEventsToList(tblName);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            setUpRecyclerView(eventList);

        }

    }

}
