package com.example.arka.arasfreezone1.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arka.arasfreezone1.R;
import com.example.arka.arasfreezone1.app;
import com.example.arka.arasfreezone1.dateConvert;
import com.example.arka.arasfreezone1.db.DatabaseHelper;
import com.example.arka.arasfreezone1.loginActivity;
import com.example.arka.arasfreezone1.models.EventModel;
import com.example.arka.arasfreezone1.services.WebService;
import com.like.LikeButton;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class eventsDetailsFragment extends Fragment {

    TextView txtTitle, txtStartDate, txtEndtDate, txtAddress, txtInfo, txtLikeCount;
    Button btnCall, btnAddtoCalender;
    ImageView imgBookmark;
    private LikeButton btnLike;

    EventModel currentModel = new EventModel();

    private int mainType;
    private String tblName;
    private int id;

    private int idUserFavorite = -1;
    private int idUserLike = -1;
    private int idUser;
    private SharedPreferences prefs;

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

        prefs = getContext().getSharedPreferences("MYPREFS", 0);
        idUser = prefs.getInt("UserId", -1);
        if (idUser > 0) {
            DatabaseCallFavoriteLike databaseCallFavoriteLike = new DatabaseCallFavoriteLike(getContext(), tblName, id);
            databaseCallFavoriteLike.execute();
        }

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

        imgBookmark.setOnClickListener(imgBookmarkClick);

        btnLike.setOnClickListener(btnLikeClick);

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
        imgBookmark = view.findViewById(R.id.imgBookmark);
        btnLike = view.findViewById(R.id.btnLike);
        txtLikeCount = view.findViewById(R.id.txtLikeCount);

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

    View.OnClickListener imgBookmarkClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (idUser > 0) {

                if (idUserFavorite > 0) {
                    imgBookmark.setImageResource(R.drawable.ic_bookmark1);
                    WebServiceCallBackFavoriteDelete favoriteDelete = new WebServiceCallBackFavoriteDelete();
                    favoriteDelete.execute();

                } else {
                    imgBookmark.setImageResource(R.drawable.ic_bookmark1_selected);
                    WebServiceCallBackFavoriteAdd webServiceCallBackFavoriteAdd = new WebServiceCallBackFavoriteAdd();
                    webServiceCallBackFavoriteAdd.execute();
                }
            }
            else{
                Intent i = new Intent(getActivity(), loginActivity.class);
                startActivity(i);
            }

        }
    };

    View.OnClickListener btnLikeClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (idUser > 0) {

                if (idUserLike > 0) {
                    btnLike.setLiked(false);
                    currentModel.likeCount--;
                    txtLikeCount.setText(currentModel.likeCount + "");
                    WebServiceCallLikeDelete likeDelete = new WebServiceCallLikeDelete();
                    likeDelete.execute();

                } else if (idUserLike < 1){
                    btnLike.setLiked(true);
                    currentModel.likeCount ++;
                    txtLikeCount.setText(currentModel.likeCount + "");
                    WebServiceCallLikeAdd webServiceCallLikeAdd = new WebServiceCallLikeAdd();
                    webServiceCallLikeAdd.execute();
                }
            }
            else{
                Intent i = new Intent(getActivity(), loginActivity.class);
                startActivity(i);
            }

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
            txtLikeCount.setText(currentModel.likeCount+ "");

        }

    }

    private class WebServiceCallBackFavoriteAdd extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.postFavorite(app.isInternetOn(), id, idUser, mainType);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (result != null) {

                if (Integer.parseInt(result) > 0) {
                    idUserFavorite = Integer.parseInt(result);
                    DatabaseCallUpdateFavorite favoriteUpdate = new DatabaseCallUpdateFavorite(getContext(), tblName, id, Integer.parseInt(result));
                    favoriteUpdate.execute();
                }
                else {
                    Toast.makeText(getContext(), "ثبت علاقه مندی نا موفق", Toast.LENGTH_LONG).show();
                    imgBookmark.setImageResource(R.drawable.ic_bookmark1);
                }

            } else {
                Toast.makeText(getContext(), "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
                imgBookmark.setImageResource(R.drawable.ic_bookmark1);
            }

        }

    }

    private class WebServiceCallBackFavoriteDelete extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.deleteFavorite(app.isInternetOn(), idUserFavorite);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (result != null) {

                if (result.equals("true")) {
                    idUserFavorite = -1;
                    DatabaseCallUpdateFavorite favoriteUpdate = new DatabaseCallUpdateFavorite(getContext(), tblName, id, -1);
                    favoriteUpdate.execute();
                }
                else {
                    Toast.makeText(getContext(), "حذف علاقه مندی نا موفق", Toast.LENGTH_LONG).show();
                    imgBookmark.setImageResource(R.drawable.ic_bookmark1_selected);
                }

            } else {
                Toast.makeText(getContext(), "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
                imgBookmark.setImageResource(R.drawable.ic_bookmark1_selected);
            }

        }

    }

    public class DatabaseCallUpdateFavorite extends AsyncTask<Object, Void, Void> {


        private DatabaseHelper databaseHelper;
        private Context context;
        private String tblName;
        int idRow, idFavorite;

        public DatabaseCallUpdateFavorite(Context context, String tblName, int idRow, int idFavorite) {
            this.context = context;
            this.tblName = tblName;
            this.idRow = idRow;
            this.idFavorite = idFavorite;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            databaseHelper = new DatabaseHelper(context);
        }


        @Override
        protected Void doInBackground(Object... objects) {

            databaseHelper.updateTblByFavorite(tblName, idRow, idFavorite);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (idUserFavorite > 0){
                imgBookmark.setImageResource(R.drawable.ic_bookmark1_selected);
            }
            else{
                imgBookmark.setImageResource(R.drawable.ic_bookmark1);
            }

        }

    }

    public class DatabaseCallFavoriteLike extends AsyncTask<Object, Void, Void> {


        private DatabaseHelper databaseHelper;
        private Context context;
        private String tblName;
        int id;

        public DatabaseCallFavoriteLike(Context context, String tblName, int id) {
            this.context = context;
            this.tblName = tblName;
            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            databaseHelper = new DatabaseHelper(context);
        }


        @Override
        protected Void doInBackground(Object... objects) {

            idUserFavorite = databaseHelper.selectFavoriteById(tblName, id);

            idUserLike = databaseHelper.selectLikeById(tblName, id);


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (idUserFavorite > 0){
                imgBookmark.setImageResource(R.drawable.ic_bookmark1_selected);
            }
            else{
                imgBookmark.setImageResource(R.drawable.ic_bookmark1);
            }

            if (idUserLike > 0){
                btnLike.setLiked(true);

            }
            else{
                btnLike.setLiked(false);
            }

        }

    }

    private class WebServiceCallLikeDelete extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String result;
//        int idLR = -1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();

//            if (idUserLike > 0)
//                idLR = idUserLike;
//            else if (idUserRate > 0 && idUserLike < 1)
//                idLR = idUserRate;
//            else
//                idLR = -1;

            // in this condition idUserLike is always > 0
        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.postLike(app.isInternetOn(), idUserLike, id, idUser, mainType, 0, -1);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (result != null) {

                if (Integer.parseInt(result) >= 0) {
                    idUserLike = -1;
                    DatabaseCallUpdateLike LikeUpdate = new DatabaseCallUpdateLike(getContext(), tblName, id, -1);
                    LikeUpdate.execute();
                }
                else {
                    Toast.makeText(getContext(), "ثبت نپسندیدن نا موفق", Toast.LENGTH_LONG).show();
                    btnLike.setLiked(true);
                    currentModel.likeCount ++;
                    txtLikeCount.setText(currentModel.likeCount + "");
                }

            } else {
                Toast.makeText(getContext(), "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
                btnLike.setLiked(true);
                currentModel.likeCount ++;
                txtLikeCount.setText(currentModel.likeCount + "");
            }

        }

    }

    public class DatabaseCallUpdateLike extends AsyncTask<Object, Void, Void> {


        private DatabaseHelper databaseHelper;
        private Context context;
        private String tblName;
        int idRow, idLike;

        public DatabaseCallUpdateLike(Context context, String tblName, int idRow, int idLike) {
            this.context = context;
            this.tblName = tblName;
            this.idRow = idRow;
            this.idLike = idLike;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            databaseHelper = new DatabaseHelper(context);
        }


        @Override
        protected Void doInBackground(Object... objects) {

            databaseHelper.updateTblByLike(tblName, idRow, idLike, currentModel.likeCount);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

//            if (idUserLike > 0){
//                btnLike.setLiked(true);
//            }
//            else{
//                btnLike.setLiked(false);
//            }

        }

    }

    private class WebServiceCallLikeAdd extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String result;
        int idLR = -1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
        }

        @Override
        protected Void doInBackground(Object... params) {

            // id is for place
            result = webService.postLike(app.isInternetOn(), idLR, id, idUser, mainType, 1, -1);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (result != null) {

                if (Integer.parseInt(result) > 0) {
                    idUserLike = Integer.parseInt(result);
                    DatabaseCallUpdateLike likeUpdate = new DatabaseCallUpdateLike(getContext(), tblName, id, Integer.parseInt(result));
                    likeUpdate.execute();
                }
                else {
                    Toast.makeText(getContext(), "ثبت پسندیدن نا موفق", Toast.LENGTH_LONG).show();
                    btnLike.setLiked(false);
                    currentModel.likeCount--;
                    txtLikeCount.setText(currentModel.likeCount + "");
                }

            } else {
                Toast.makeText(getContext(), "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
                btnLike.setLiked(false);
                currentModel.likeCount--;
                txtLikeCount.setText(currentModel.likeCount + "");
            }

        }

    }


    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences prefs = getContext().getSharedPreferences("MYPREFS", 0);
        idUser = prefs.getInt("UserId", -1);
        if (idUser > 0) {
            DatabaseCallFavoriteLike databaseCallFavoriteLike = new DatabaseCallFavoriteLike(getContext(), tblName, id);
            databaseCallFavoriteLike.execute();
        }
    }
}
