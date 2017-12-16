package com.example.arka.arasfreezone1.fragments;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arka.arasfreezone1.R;
import com.example.arka.arasfreezone1.app;
import com.example.arka.arasfreezone1.dateConvert;
import com.example.arka.arasfreezone1.db.DatabaseHelper;
import com.example.arka.arasfreezone1.models.EventModel;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class eventsDetailsFragment extends Fragment {

    TextView txtTitle, txtStartDate, txtEndtDate, txtAddress, txtInfo;
    Button btnCall, btnAddtoCalender;

    EventModel currentModel = new EventModel();

    private int mainType;
    private String tblName;
    private int id;

    public eventsDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events_details, container, false);
        initView(view);

//        Bundle args = getArguments();
//        currentModel.id = args.getInt("id");
//        currentModel.body = args.getString("body");
//        currentModel.title = args.getString("title");
//        currentModel.startTime = args.getString("startTime");
//        currentModel.startDate = args.getInt("startDate");
//        currentModel.endTime = args.getString("endTime");
//        currentModel.endDate = args.getInt("endDate");
//        currentModel.lat = args.getInt("lat");
//        currentModel.lon = args.getInt("lon");
//        currentModel.place = args.getString("place");
//        currentModel.address = args.getString("address");
//        currentModel.phone = args.getString("phone");
//        currentModel.website = args.getString("website");
//        currentModel.visibility = args.getBoolean("visibility");

        Bundle args = getArguments();
        id = args.getInt("ID");
        tblName = args.getString("TBL_NAME");
        mainType = 10;

        //setViews();

        DatabaseCallback databaseCallback = new DatabaseCallback(getContext(), tblName, id);
        databaseCallback.execute();

        btnAddtoCalender.setOnClickListener(btnCalenderClick);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentModel.phone != null) {
                    if (currentModel.phone.length() > 0) {
                        Intent intentCall = new Intent(Intent.ACTION_DIAL);
                        intentCall.setData(Uri.fromParts("tel", currentModel.phone, null));
                        startActivity(intentCall);
                    }
                    else
                        Toast.makeText(getContext(), "شماره تلفن موجود نیست", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getContext(), "شماره تلفن موجود نیست", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    private void initView(View view){

        txtTitle = view.findViewById(R.id.txtTitle);
        txtStartDate = view.findViewById(R.id.txtStartDate);
        txtEndtDate = view.findViewById(R.id.txtEndtDate);
        txtAddress = view.findViewById(R.id.txtAddress);
        txtInfo = view.findViewById(R.id.txtInfo);
        btnAddtoCalender = view.findViewById(R.id.btnAddtoCalender);
        btnCall = view.findViewById(R.id.btnCall);

    }

    private void setViews(){

        txtTitle.setText(currentModel.title);
        txtStartDate.setText(app.changeDateToString(currentModel.startDate) + " ساعت " + currentModel.startTime);
        txtEndtDate.setText(app.changeDateToString(currentModel.endDate )+ " ساعت " + currentModel.endTime);
        txtAddress.setText(currentModel.title);
        txtInfo.setText(currentModel.title);

    }

    View.OnClickListener btnCalenderClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String yearStart = String.valueOf(currentModel.startDate).substring(0,4);
            String monthStart = String.valueOf(currentModel.startDate).substring(4,6);
            String dayStart = String.valueOf(currentModel.startDate).substring(6,8);

            String yearEnd = String.valueOf(currentModel.endDate).substring(0,4);
            String monthEnd = String.valueOf(currentModel.endDate).substring(4,6);
            String dayEnd = String.valueOf(currentModel.endDate).substring(6,8);

            String gregorianDate;

            dateConvert convert = new dateConvert();
            convert.PersianToGregorian(Integer.parseInt(yearStart), Integer.parseInt(monthStart), Integer.parseInt(dayStart));
            gregorianDate = convert.toString().replace("/", "");

            Calendar beginTime = Calendar.getInstance();
            beginTime.set(Integer.parseInt(gregorianDate.substring(0, 4)), Integer.parseInt(gregorianDate.substring(4, 6)), Integer.parseInt(gregorianDate.substring(6, 8)), Integer.parseInt(currentModel.startTime.substring(0, 2)), Integer.parseInt(currentModel.startTime.substring(3, 5)));

            convert.PersianToGregorian(Integer.parseInt(yearEnd), Integer.parseInt(monthEnd), Integer.parseInt(dayEnd));
            gregorianDate = convert.toString().replace("/", "");

            Calendar endTime = Calendar.getInstance();
            endTime.set(Integer.parseInt(gregorianDate.substring(0, 4)), Integer.parseInt(gregorianDate.substring(4, 6)), Integer.parseInt(gregorianDate.substring(6, 8)), Integer.parseInt(currentModel.endTime.substring(0, 2)), Integer.parseInt(currentModel.endTime.substring(3, 5)));

            Intent intent = new Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                    .putExtra(CalendarContract.Events.TITLE, currentModel.title)
                    .putExtra(CalendarContract.Events.DESCRIPTION, currentModel.body)
                    .putExtra(CalendarContract.Events.EVENT_LOCATION, currentModel.address)
                    .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                    .putExtra(Intent.EXTRA_EMAIL, currentModel.website);
            startActivity(intent);

        }
    };


    public class DatabaseCallback extends AsyncTask<Object, Void, Void> {


        private DatabaseHelper databaseHelper;
        private Context context;
        private String tblName;
        int id;

        public DatabaseCallback(Context context, String tblName, int id) {
            this.context = context;
            this.tblName = tblName;
            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            currentModel = new EventModel();
            databaseHelper = new DatabaseHelper(context);
        }


        @Override
        protected Void doInBackground(Object... objects) {

            currentModel = databaseHelper.selectEventsDetail(tblName, id);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            txtTitle.setText(currentModel.title);
            txtStartDate.setText(app.changeDateToString(currentModel.startDate) + " ساعت " + currentModel.startTime);
            txtEndtDate.setText(app.changeDateToString(currentModel.endDate )+ " ساعت " + currentModel.endTime);
            txtAddress.setText(currentModel.title);
            txtInfo.setText(currentModel.title);

        }

    }


}
