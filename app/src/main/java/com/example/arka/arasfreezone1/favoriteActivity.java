package com.example.arka.arasfreezone1;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.arka.arasfreezone1.adapter.eventsListAdapter;
import com.example.arka.arasfreezone1.adapter.restaurantListAdapter;
import com.example.arka.arasfreezone1.db.DatabaseHelper;
import com.example.arka.arasfreezone1.models.EventModel;
import com.example.arka.arasfreezone1.models.PlacesModel;

import java.util.ArrayList;
import java.util.List;

public class favoriteActivity extends AppCompatActivity {

    private RelativeLayout lytSearch;
    private RelativeLayout lytBack;
    private LinearLayout lytMain;
    private RecyclerView recycle;
    private LinearLayout lytQuestionSend;
    private LinearLayout lytEmpty;
    private LinearLayout lytDisconnect;
    private RecyclerView recycleEvents, recyclePlaces;

    private List<EventModel> eventList;
    private List<PlacesModel> placesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        initView();

        recycleEvents.setNestedScrollingEnabled(false);
        recyclePlaces.setNestedScrollingEnabled(false);

        DatabaseCallback databaseCallback = new DatabaseCallback(favoriteActivity.this);
        databaseCallback.execute();

        lytBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.activity_back_enter);
    }

    private void initView() {
        lytSearch = (RelativeLayout) findViewById(R.id.lytSearch);
        lytBack = (RelativeLayout) findViewById(R.id.lytBack);
        lytMain = (LinearLayout) findViewById(R.id.lytMain);
        recycle = (RecyclerView) findViewById(R.id.recycle);
        lytQuestionSend = (LinearLayout) findViewById(R.id.lytQuestionSend);
        lytEmpty = (LinearLayout) findViewById(R.id.lytEmpty);
        lytDisconnect = (LinearLayout) findViewById(R.id.lytDisconnect);
        recyclePlaces = findViewById(R.id.recyclePlaces);
        recycleEvents = findViewById(R.id.recycleEvents);
    }

    private void setUpRecyclerViewEvent(List<EventModel> list) {

        eventsListAdapter adapter = new eventsListAdapter(app.context, list, false);
        recycleEvents.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(app.context, 2);
        recycleEvents.setLayoutManager(gridLayoutManager);
    }

    private void setUpRecyclerViewPlaces(List<PlacesModel> placesList) {

        restaurantListAdapter adapter = new restaurantListAdapter(favoriteActivity.this, placesList, "Tbl_Eating");
        recyclePlaces.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(favoriteActivity.this);
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclePlaces.setLayoutManager(mLinearLayoutManagerVertical);
    }

    public class DatabaseCallback extends AsyncTask<Object, Void, Void> {


        private DatabaseHelper databaseHelper;
        private Context context;
        private String tblName;

        public DatabaseCallback(Context context) {
            this.context = context;
            //this.tblName = tblName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            eventList = new ArrayList<>();
            placesList = new ArrayList<>();
            databaseHelper = new DatabaseHelper(context);
        }


        @Override
        protected Void doInBackground(Object... objects) {

            eventList = databaseHelper.selectAllEventsByFavorite("Tbl_Events");

            placesList.addAll(databaseHelper.selectAllPlacesByFavorite("Tbl_Eating"));
            placesList.addAll(databaseHelper.selectAllPlacesByFavorite("Tbl_Shoppings"));
            placesList.addAll(databaseHelper.selectAllPlacesByFavorite("Tbl_Tourisms"));
            placesList.addAll(databaseHelper.selectAllPlacesByFavorite("Tbl_Culturals"));
            placesList.addAll(databaseHelper.selectAllPlacesByFavorite("Tbl_Transports"));
            //placesList.addAll(databaseHelper.selectAllPlacesByFavorite("Tbl_Offices"));
            placesList.addAll(databaseHelper.selectAllPlacesByFavorite("Tbl_Medicals"));
            placesList.addAll(databaseHelper.selectAllPlacesByFavorite("Tbl_Services"));

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (eventList != null) {
                if (eventList.size() > 0) {
                    setUpRecyclerViewEvent(eventList);
                }
            }
            if (placesList != null) {
                if (placesList.size() > 0) {
                    setUpRecyclerViewPlaces(placesList);
                }
            }
            if (eventList != null && placesList != null) {
                if (eventList.size() == 0 && placesList.size() == 0) {
                    lytMain.setVisibility(View.GONE);
                    lytEmpty.setVisibility(View.VISIBLE);
                }
            }


        }

    }


}
