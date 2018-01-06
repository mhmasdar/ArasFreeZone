package com.example.arka.arasfreezone1;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.arka.arasfreezone1.adapter.SlidingImage_Adapter;
import com.example.arka.arasfreezone1.db.DatabaseHelper;
import com.example.arka.arasfreezone1.models.HomePageModel;
import com.example.arka.arasfreezone1.models.ReligiousTimesModel;
import com.example.arka.arasfreezone1.models.WeatherModel;
import com.example.arka.arasfreezone1.services.WeatherService;
import com.example.arka.arasfreezone1.services.WebService;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private ViewPagerCustomDuration mPager;
    private static int currentPage = 0;
    private RelativeLayout relative_Menu;
    private LinearLayout lytWeather;
    TextView cityField, txtPray, detailsField, currentTemperatureField, humidity_field, pressure_field, weatherIcon, updatedField;
    private boolean checkWeather = true;
    private CirclePageIndicator indicator;
    private Dialog dialog;
    private List<HomePageModel> homePageModelList;
    private TextView txtSobh;
    private TextView txttolo;
    private TextView txtZohr;
    private TextView txtGhorob;
    private TextView txtMaghreb;
    private TextView txtNimeShab;
    private Button btnCancel;
    private LinearLayout lytLoading, lytMain, lytDisconnect;
    private WeatherServiceCallBack WcallBack;

    private ReligiousTimesModel timesModel;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        currentTemperatureField = (TextView) view.findViewById(R.id.current_temperature_field);
        humidity_field = (TextView) view.findViewById(R.id.humidity_field);
        weatherIcon = (TextView) view.findViewById(R.id.weather_icon);
        txtPray = (TextView) view.findViewById(R.id.txtPray);
        relative_Menu = (RelativeLayout) view.findViewById(R.id.relative_Menu);
        lytWeather = (LinearLayout) view.findViewById(R.id.lytWeather);
        mPager = (ViewPagerCustomDuration) view.findViewById(R.id.pager);
        indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);

        DatabaseCallback databaseCallback = new DatabaseCallback(getContext());
        databaseCallback.execute();

        //initSlider();


        txtPray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPrayDialog();
            }
        });

        if (checkWeather && app.isInternetOn()) {
            WcallBack = new WeatherServiceCallBack();
            WcallBack.execute();
        }

        relative_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ((MainActivity)getActivity()).drawer();
                Intent mapIntent = new Intent(getActivity(), navigationDrawerActivity.class);
                startActivity(mapIntent);
                getActivity().overridePendingTransition(R.anim.bottom_to_top, R.anim.stay);
            }
        });


        return view;
    }

    private void initView() {

    }


    private class WeatherServiceCallBack extends AsyncTask<Object, Void, Void> {

        private WeatherService weatherService;
        private WeatherModel weatherModel;
        private WebService webService;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weatherService = new WeatherService();
            webService = new WebService();
        }

        @Override
        protected Void doInBackground(Object... params) {

            weatherModel = weatherService.getCurrentWeather();
            timesModel = webService.getReligiousTimes(app.isInternetOn());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //set weather
            if (weatherModel != null) {
                currentTemperatureField.setText(weatherModel.temperature);
                humidity_field.setText(weatherModel.humidity);
                weatherIcon.setText(Html.fromHtml(weatherModel.iconText));
                checkWeather = false;
                lytWeather.setVisibility(View.VISIBLE);
            }

            //set times


        }
    }


    private void initSlider() {


        mPager.setAdapter(new SlidingImage_Adapter(getContext(), homePageModelList));
        indicator.setViewPager(mPager);
        final float density = getResources().getDisplayMetrics().density;

        //Set circle indicator radius
        indicator.setRadius(5 * density);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 3) //slider is on the last image
                    currentPage = 0;

                mPager.setCurrentItem(currentPage++, true);
            }
        };

        app.isScheduled = false;

        if (app.isScheduled == false) {
            app.swipeTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 2000, 4000);
            app.isScheduled = true;
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

    public class DatabaseCallback extends AsyncTask<Object, Void, Void> {


        private DatabaseHelper databaseHelper;
        private Context context;
//        private String tblName;
//        int id;

        public DatabaseCallback(Context context) {
            this.context = context;
//            this.tblName = tblName;
//            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            homePageModelList = new ArrayList<>();
            databaseHelper = new DatabaseHelper(context);
        }


        @Override
        protected Void doInBackground(Object... objects) {

            homePageModelList = databaseHelper.selectAllHomePages();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

//            if (homePageModelList != null) {

            initSlider();
//            }

        }

    }


    private void showPrayDialog() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_pray);
        txtSobh = (TextView) dialog.findViewById(R.id.txtSobh);
        txttolo = (TextView) dialog.findViewById(R.id.txttolo);
        txtZohr = (TextView) dialog.findViewById(R.id.txtZohr);
        txtGhorob = (TextView) dialog.findViewById(R.id.txtGhorob);
        txtMaghreb = (TextView) dialog.findViewById(R.id.txtMaghreb);
        txtNimeShab = (TextView) dialog.findViewById(R.id.txtNimeShab);
        btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        lytDisconnect = dialog.findViewById(R.id.lytDisconnect);
        lytMain = dialog.findViewById(R.id.lytMain);
        lytLoading = dialog.findViewById(R.id.lytLoading);


        if (timesModel != null){

            txtSobh.setText(timesModel.Imsaak);
            txttolo.setText(timesModel.Sunrise);
            txtZohr.setText(timesModel.Noon);
            txtGhorob.setText(timesModel.Sunset);
            txtMaghreb.setText(timesModel.Maghreb);
            txtNimeShab.setText(timesModel.Midnight);


        }
        else{
            lytDisconnect.setVisibility(View.VISIBLE);
            lytMain.setVisibility(View.GONE);
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onStop() {
        super.onStop();

        if(WcallBack != null && WcallBack.getStatus() == AsyncTask.Status.RUNNING)
            WcallBack.cancel(true);
    }
}
