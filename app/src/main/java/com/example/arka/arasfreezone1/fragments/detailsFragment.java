package com.example.arka.arasfreezone1.fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arka.arasfreezone1.R;
import com.example.arka.arasfreezone1.adapter.detailsSliderAdapter;
import com.example.arka.arasfreezone1.adapter.facilityDialogAdapter;
import com.example.arka.arasfreezone1.adapter.menuDialogAdapter;
import com.example.arka.arasfreezone1.app;
import com.example.arka.arasfreezone1.commentsActivity;
import com.example.arka.arasfreezone1.db.DatabaseHelper;
import com.example.arka.arasfreezone1.loginActivity;
import com.example.arka.arasfreezone1.models.FacilityModel;
import com.example.arka.arasfreezone1.models.MenuModel;
import com.example.arka.arasfreezone1.models.PlacesModel;
import com.example.arka.arasfreezone1.services.WebService;
import com.like.LikeButton;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class detailsFragment extends Fragment {

    private ViewPager mPager;
    private int currentPage = 0;
    private boolean detailsSlider = false;
    public Timer swipeTimer = new Timer();
    private LinearLayout lytRating;
    private LinearLayout lytGallery;
    private TextView txtName;
    private ImageView imgShare;
    private ImageView imgBookmark;
    private ImageView imgBack;
    private LikeButton btnLike;
    private TextView txtLikeCount;
    private LinearLayout lytCall;
    private LinearLayout lytMenu;
    private LinearLayout lytLocation;
    private TextView txtAddress;
    private TextView txtInfo, txtHour, txtDay;
    private LinearLayout lytWebsite;
    private LinearLayout lytOptions;
    private LinearLayout lytComments;
    private LinearLayout lytDrivers;
    private ImageView imgMenuAndCost;
    private TextView txtMenuAndCost;
    RatingBar rateBar;

    SharedPreferences prefs;

    private int mainType;
    private String tblName;
    private int id;
    private PlacesModel placesModel;
    private int idUserFavorite = -1;
    private int idUserLike = -1;
    private int idUserRate = -1;
    private double userRate = -1;
    private int idUser;

    //recycler in dialog_menu
    private RecyclerView recyclerMenu;
    List<MenuModel> menuList;

    //recycler in dialog_Facility
    private RecyclerView recyclerFacility;
    List<FacilityModel> facilityList;

    public detailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        Bundle args = getArguments();
        id = args.getInt("ID");
        tblName = args.getString("TBL_NAME");
        mainType = getMainType(tblName);

        initView(view);

        lytMenu.setVisibility(View.GONE);
        if (tblName.equals("Tbl_Tourisms") || tblName.equals("Tbl_Rests") || tblName.equals("Tbl_Transports") || tblName.equals("Tbl_Eating"))
            lytMenu.setVisibility(View.VISIBLE);

        DatabaseCallback databaseCallback = new DatabaseCallback(getContext(), tblName, id);
        databaseCallback.execute();

        prefs = getContext().getSharedPreferences("MYPREFS", 0);
        idUser = prefs.getInt("UserId", -1);
        if (idUser > 0) {
            DatabaseCallFavoriteLikeRate databaseCallFavoriteLikeRate = new DatabaseCallFavoriteLikeRate(getContext(), tblName, id);
            databaseCallFavoriteLikeRate.execute();
        }

        Animation fade_in = AnimationUtils.loadAnimation(getContext(), R.anim.details_gallery_layout);
        lytGallery.startAnimation(fade_in);


        lytGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryFragment fragment = new galleryFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container2, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });


        lytRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRatingDialog();
            }
        });


        lytComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), commentsActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.fragment_enter, R.anim.fragment_exit);
            }
        });

        lytCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCall = new Intent(Intent.ACTION_DIAL);
                intentCall.setData(Uri.fromParts("tel", placesModel.phone, null));
                startActivity(intentCall);
            }
        });

        lytWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "";
                if (placesModel.website != null && !placesModel.website.equals("")) {
                    url = placesModel.website;

                    if (!url.startsWith("http://") && !url.startsWith("https://"))
                        url = "http://" + url;
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                } else {
                    Toast.makeText(getContext(), "وب سایت موجود نمی باشد", Toast.LENGTH_LONG).show();
                }
            }
        });

        lytMenu.setOnClickListener(lytMenuClick);

        lytOptions.setOnClickListener(lytOptionsClick);

        imgBookmark.setOnClickListener(imgBookmarkClick);

        btnLike.setOnClickListener(btnLikeClick);

        initSlider(view);

        return view;
    }

    private void initSlider(View view) {

        mPager = (ViewPager) view.findViewById(R.id.pager);


        mPager.setAdapter(new detailsSliderAdapter(app.context));


        CirclePageIndicator indicator = (CirclePageIndicator)
                view.findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

        //Set circle indicator radius
        indicator.setRadius(3 * density);


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
        if (detailsSlider == false) {
            swipeTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 2000, 3000);
            detailsSlider = true;
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

    private void showRatingDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_rating);
        RatingBar rating_dialog = (RatingBar) dialog.findViewById(R.id.rating_dialog);
        Button btnSubmitRating = (Button) dialog.findViewById(R.id.btnSubmitRating);
        Button btnCancelRating = (Button) dialog.findViewById(R.id.btnCancelRating);
        btnCancelRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSubmitRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private void initView(View view) {
        lytRating = (LinearLayout) view.findViewById(R.id.lytRating);
        lytGallery = (LinearLayout) view.findViewById(R.id.lytGallery);
        lytDrivers = (LinearLayout) view.findViewById(R.id.lytDrivers);
        txtName = (TextView) view.findViewById(R.id.txtName);
        imgShare = (ImageView) view.findViewById(R.id.imgShare);
        imgBookmark = (ImageView) view.findViewById(R.id.imgBookmark);
        imgBack = (ImageView) view.findViewById(R.id.imgBack);
        btnLike = (LikeButton) view.findViewById(R.id.btnLike);
        txtLikeCount = (TextView) view.findViewById(R.id.txtLikeCount);
        lytCall = (LinearLayout) view.findViewById(R.id.lytCall);
        lytMenu = (LinearLayout) view.findViewById(R.id.lytMenu);
        lytLocation = (LinearLayout) view.findViewById(R.id.lytLocation);
        txtAddress = (TextView) view.findViewById(R.id.txtAddress);
        txtInfo = (TextView) view.findViewById(R.id.txtInfo);
        lytWebsite = (LinearLayout) view.findViewById(R.id.lytWebsite);
        lytOptions = (LinearLayout) view.findViewById(R.id.lytOptions);
        lytComments = (LinearLayout) view.findViewById(R.id.lytComments);
        txtDay = view.findViewById(R.id.txtDay);
        txtHour = view.findViewById(R.id.txtHour);
        txtMenuAndCost = view.findViewById(R.id.txtMenuAndCost);
        imgMenuAndCost = view.findViewById(R.id.imgMenuAndCost);
        rateBar = view.findViewById(R.id.rateBar);
    }

    private int getMainType(String name) {
        int type = 0;

        assert name != null;
        switch (name) {
            case "Tbl_Eating":
                type = 1;
                break;
            case "Tbl_Shoppings":
                type = 2;
                break;
            case "Tbl_Rests":
                type = 3;
                break;
            case "Tbl_Tourisms":
                type = 4;
                break;
            case "Tbl_Culturals":
                type = 5;
                break;
            case "Tbl_Transports":
                type = 6;
                break;
            case "Tbl_Services":
                type = 7;
                break;
            case "Tbl_Offices":
                type = 8;
                break;
            case "Tbl_Medicals":
                type = 9;
                break;
            default:
        }

        return type;

    }

    View.OnClickListener lytMenuClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (!tblName.equals("Tbl_Transports")) {

                Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_menu);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();

                recyclerMenu = dialog.findViewById(R.id.recycler);

                WebServiceCallBackMenu webServiceCallBackMenu = new WebServiceCallBackMenu();
                webServiceCallBackMenu.execute();
            } else {
                //todo: goto drivers fragment
            }

        }
    };

    View.OnClickListener lytOptionsClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Dialog dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_facilities);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();

            recyclerFacility = dialog.findViewById(R.id.recycler);

            WebServiceCallBackFacilities webServiceCallBackFacilities = new WebServiceCallBackFacilities();
            webServiceCallBackFacilities.execute();

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
                    WebServiceCallLikeDelete likeDelete = new WebServiceCallLikeDelete();
                    likeDelete.execute();

                } else if (idUserRate > 0 && idUserLike < 1){
                    btnLike.setLiked(true);
                    WebServiceCallLikeAdd webServiceCallLikeAdd = new WebServiceCallLikeAdd();
                    webServiceCallLikeAdd.execute();
                } else if (idUserRate < 1 && idUserLike < 1){
                    btnLike.setLiked(true);
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

    private void setUpRecyclerViewMenu(List<MenuModel> menuList) {

        menuDialogAdapter adapter = new menuDialogAdapter(getContext(), menuList);
        recyclerMenu.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerMenu.setLayoutManager(mLinearLayoutManagerVertical);
    }

    private void setUpRecyclerViewFacilities(List<FacilityModel> facilityList) {

        facilityDialogAdapter adapter = new facilityDialogAdapter(getContext(), facilityList);
        recyclerFacility.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerFacility.setLayoutManager(mLinearLayoutManagerVertical);
    }

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
            placesModel = new PlacesModel();
            databaseHelper = new DatabaseHelper(context);
        }


        @Override
        protected Void doInBackground(Object... objects) {

            placesModel = databaseHelper.selectPlacesDetail(tblName, id);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            txtLikeCount.setText(placesModel.likeCount + "");
            txtAddress.setText("آدرس: " + placesModel.address);
            txtInfo.setText(placesModel.info);
            txtName.setText(placesModel.name);
            txtHour.setText("از" + placesModel.startTime + "الی" + placesModel.endTime);
            if (tblName.equals("Tbl_Tourisms")) {
                imgMenuAndCost.setImageResource(R.drawable.cost);
                txtMenuAndCost.setText(placesModel.cost + "ریال");
            }
            if (tblName.equals("Tbl_Transports")) {
                imgMenuAndCost.setImageResource(R.drawable.ic_detail_menu);
                txtMenuAndCost.setText("راننده ها");
            }


            switch (placesModel.idStartDay) {
                case 1:
                    txtDay.setText("شنبه تا ");
                    break;
                case 2:
                    txtDay.setText("یکشنبه تا ");
                    break;
                case 3:
                    txtDay.setText("دوشنبه تا ");
                    break;
                case 4:
                    txtDay.setText("سه شنبه تا ");
                    break;
                case 5:
                    txtDay.setText("جهارشنبه تا ");
                    break;
                case 6:
                    txtDay.setText("پنجشنبه تا ");
                    break;
                case 7:
                    txtDay.setText("جمعه تا ");
                    break;
            }
            switch (placesModel.idEndDay) {
                case 1:
                    txtDay.setText(txtDay.getText().toString() + "شنبه");
                    break;
                case 2:
                    txtDay.setText(txtDay.getText().toString() + "یکشنبه");
                    break;
                case 3:
                    txtDay.setText(txtDay.getText().toString() + "دوشنبه");
                    break;
                case 4:
                    txtDay.setText(txtDay.getText().toString() + "سه شنبه");
                    break;
                case 5:
                    txtDay.setText(txtDay.getText().toString() + "جهارشنبه");
                    break;
                case 6:
                    txtDay.setText(txtDay.getText().toString() + "پنجشنبه");
                    break;
                case 7:
                    txtDay.setText(txtDay.getText().toString() + "جمعه");
                    break;
            }

        }

    }

    public class DatabaseCallFavoriteLikeRate extends AsyncTask<Object, Void, Void> {


        private DatabaseHelper databaseHelper;
        private Context context;
        private String tblName;
        int id;

        public DatabaseCallFavoriteLikeRate(Context context, String tblName, int id) {
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

            idUserRate = databaseHelper.selectRateById(tblName, id);

            userRate = databaseHelper.selectRateValueById(tblName, id);

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
            if (idUserRate > 0){
               rateBar.setRating((float) userRate);

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

            databaseHelper.updateTblByLike(tblName, idRow, idLike);

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

    private class WebServiceCallBackMenu extends AsyncTask<Object, Void, Void> {

        private WebService webService;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            menuList = new ArrayList<>();
            webService = new WebService();
        }

        @Override
        protected Void doInBackground(Object... params) {

            menuList = webService.getMenu(app.isInternetOn(), mainType, placesModel.id);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (menuList != null) {

                if (menuList.size() > 0)
                    setUpRecyclerViewMenu(menuList);
                else
                    Toast.makeText(getContext(), "موردی وجود ندارد", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getContext(), "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
            }

        }

    }

    private class WebServiceCallBackFacilities extends AsyncTask<Object, Void, Void> {

        private WebService webService;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            facilityList = new ArrayList<>();
            webService = new WebService();
        }

        @Override
        protected Void doInBackground(Object... params) {

            facilityList = webService.getfacility(app.isInternetOn(), mainType, placesModel.id);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (facilityList != null) {

                if (facilityList.size() > 0)
                    setUpRecyclerViewFacilities(facilityList);
                else
                    Toast.makeText(getContext(), "موردی وجود ندارد", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getContext(), "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
            }

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

    private class WebServiceCallLikeAdd extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String result;
        int idLR = -1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            if (idUserLike > 0)
                idLR = idUserLike;
            else if (idUserRate > 0 && idUserLike < 1)
                idLR = idUserRate;
            else
                idLR = -1;
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
                }

            } else {
                Toast.makeText(getContext(), "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
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

                if (result.equals("true")) {
                    idUserFavorite = -1;
                    DatabaseCallUpdateLike LikeUpdate = new DatabaseCallUpdateLike(getContext(), tblName, id, -1);
                    LikeUpdate.execute();
                }
                else {
                    Toast.makeText(getContext(), "ثبت نپسندیدن نا موفق", Toast.LENGTH_LONG).show();
                    btnLike.setLiked(true);
                }

            } else {
                Toast.makeText(getContext(), "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
                btnLike.setLiked(true);
            }

        }

    }

    @Override
    public void onResume() {
        super.onResume();

        prefs = getContext().getSharedPreferences("MYPREFS", 0);
        idUser = prefs.getInt("UserId", -1);
        if (idUser > 0) {
            DatabaseCallFavoriteLikeRate databaseCallFavoriteLikeRate = new DatabaseCallFavoriteLikeRate(getContext(), tblName, id);
            databaseCallFavoriteLikeRate.execute();
        }
    }
}
