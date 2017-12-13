package com.example.arka.arasfreezone1.fragments;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arka.arasfreezone1.R;
import com.example.arka.arasfreezone1.adapter.detailsSliderAdapter;
import com.example.arka.arasfreezone1.app;
import com.example.arka.arasfreezone1.db.DatabaseHelper;
import com.example.arka.arasfreezone1.models.PlacesModel;
import com.like.LikeButton;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class detailsOfficeFragment extends Fragment {

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
    ImageView imgMenuAndCost;
    TextView txtMenuAndCost;

    String tblName;
    int id;
    PlacesModel placesModel;

    public detailsOfficeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details_office, container, false);

        Bundle args = getArguments();
        id = args.getInt("ID");
        tblName = args.getString("TBL_NAME");

        initView(view);

        DatabaseCallbackOffice databaseCallbackOffice = new DatabaseCallbackOffice(getContext(), tblName, id);
        databaseCallbackOffice.execute();

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

    private void initView(View view) {

        lytGallery = (LinearLayout) view.findViewById(R.id.lytGallery);
        txtName = (TextView) view.findViewById(R.id.txtName);
        imgShare = (ImageView) view.findViewById(R.id.imgShare);
        imgBookmark = (ImageView) view.findViewById(R.id.imgBookmark);
        imgBack = (ImageView) view.findViewById(R.id.imgBack);
        btnLike = (LikeButton) view.findViewById(R.id.btnLike);
        txtLikeCount = (TextView) view.findViewById(R.id.txtLikeCount);
        lytCall = (LinearLayout) view.findViewById(R.id.lytCall);
        lytLocation = (LinearLayout) view.findViewById(R.id.lytLocation);
        txtAddress = (TextView) view.findViewById(R.id.txtAddress);
        txtInfo = (TextView) view.findViewById(R.id.txtInfo);
        lytWebsite = (LinearLayout) view.findViewById(R.id.lytWebsite);

    }

    public class DatabaseCallbackOffice extends AsyncTask<Object, Void, Void> {


        private DatabaseHelper databaseHelper;
        private Context context;
        private String tblName;
        int id;

        public DatabaseCallbackOffice(Context context, String tblName, int id) {
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

            placesModel = databaseHelper.selectOfficesDetail(tblName, id);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            txtAddress.setText("آدرس: " + placesModel.address);
            txtInfo.setText(placesModel.info);
            txtName.setText(placesModel.name);

        }

    }


}
