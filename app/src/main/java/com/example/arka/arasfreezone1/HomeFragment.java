package com.example.arka.arasfreezone1;


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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.arka.arasfreezone1.adapter.SlidingImage_Adapter;
import com.example.arka.arasfreezone1.models.ReligiousTimesModel;
import com.example.arka.arasfreezone1.models.WeatherModel;
import com.example.arka.arasfreezone1.services.WeatherService;
import com.example.arka.arasfreezone1.services.WebService;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private ViewPager mPager;
    private static int currentPage = 0;
    private RelativeLayout relative_Menu;
    TextView cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, weatherIcon, updatedField;
    private boolean checkWeather = true;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        currentTemperatureField = (TextView)view.findViewById(R.id.current_temperature_field);
        humidity_field = (TextView)view.findViewById(R.id.humidity_field);
        weatherIcon = (TextView)view.findViewById(R.id.weather_icon);

        if (checkWeather && app.isInternetOn()) {
            WeatherServiceCallBack WcallBack = new WeatherServiceCallBack();
            WcallBack.execute();
        }



        mPager = (ViewPager) view.findViewById(R.id.pager);
        relative_Menu = (RelativeLayout) view.findViewById(R.id.relative_Menu);
        mPager.setAdapter(new SlidingImage_Adapter(getContext()));


        relative_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ((MainActivity)getActivity()).drawer();
                Intent mapIntent = new Intent(getActivity(), navigationDrawerActivity.class);
                startActivity(mapIntent);
                getActivity().overridePendingTransition(R.anim.bottom_to_top, R.anim.stay);
            }
        });

        CirclePageIndicator indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
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
        if (app.isScheduled ==false) {
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

        return view;
    }


    private class WeatherServiceCallBack extends AsyncTask<Object, Void, Void> {

        private WeatherService weatherService;
        private WeatherModel weatherModel;
        private WebService webService;
        private ReligiousTimesModel timesModel;

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
            currentTemperatureField.setText(weatherModel.temperature);
            humidity_field.setText(weatherModel.pressure + " / " + weatherModel.humidity);
            weatherIcon.setText(Html.fromHtml(weatherModel.iconText));
            checkWeather = false;

            //set times


        }
    }


}
