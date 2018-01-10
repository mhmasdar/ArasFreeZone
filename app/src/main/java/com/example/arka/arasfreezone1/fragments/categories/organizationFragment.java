package com.example.arka.arasfreezone1.fragments.categories;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arka.arasfreezone1.R;
import com.example.arka.arasfreezone1.RoutingActivity;
import com.example.arka.arasfreezone1.ViewPagerCustomDuration;
import com.example.arka.arasfreezone1.adapter.organizationAdapter;
import com.example.arka.arasfreezone1.adapter.organizationSliderAdapter;
import com.example.arka.arasfreezone1.adapter.restaurantListAdapter;
import com.example.arka.arasfreezone1.app;
import com.example.arka.arasfreezone1.db.DatabaseHelper;
import com.example.arka.arasfreezone1.models.ImgModel;
import com.example.arka.arasfreezone1.models.PhoneModel;
import com.example.arka.arasfreezone1.models.PlacesModel;
import com.viewpagerindicator.CirclePageIndicator;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class organizationFragment extends Fragment {


    private ViewPager mPager;
    private int currentPage = 0;
    private RelativeLayout organizationSlider;
    private CirclePageIndicator indicator;
    private LinearLayout lytOrgIntroduce;
    private LinearLayout lytOrgAddress;
    private LinearLayout lytOrgPhone;
    private LinearLayout lytOrgWeb;
    private ExpandableLayout expanableLayout1, expanableLayout2, expanableLayout3;
    private boolean openCheck1 = false, openCheck2 = false, openCheck3 = false;
    private int height;
    private boolean OrganiztionSlider = false;
    private ViewPagerCustomDuration pager;
    private TextView txtOrgintroduce;
    private TextView txtOrgAddress;
    private static Timer swipeTimer = new Timer();
    private ImageView imgBack;
    private LinearLayout lytRoute;
    private RecyclerView Recycler;

    PlacesModel placesModel;
    List<PhoneModel> phoneList;
    private List<ImgModel> imgList;
    private DatabaseCallback databaseCallback;
    private DatabaseCallbackPhones CallbackPhones;
    Dialog dialog;

    public organizationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_organization, container, false);
        initView(view);
        //initSlider(view);

        databaseCallback = new DatabaseCallback(getContext());
        databaseCallback.execute();

        CallbackPhones = new DatabaseCallbackPhones(getContext());
        CallbackPhones.execute();

        //setUpRecyclerView();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });

        lytOrgIntroduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!openCheck1) {
                    height = lytOrgIntroduce.getHeight(); // get height of layout
                    setLayoutsHeight();
                    expanableLayout1.expand();

                    openCheck1 = true;
                } else {
                    expanableLayout1.setExpanded(false);
                    openCheck1 = false;
                }
            }
        });

        lytOrgAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!openCheck2) {
                    height = lytOrgAddress.getHeight(); // get height of layout
                    setLayoutsHeight();
                    expanableLayout2.expand();

                    openCheck2 = true;
                } else {
                    expanableLayout2.setExpanded(false);
                    openCheck2 = false;
                }
            }
        });


        lytOrgPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!openCheck3) {
                    height = lytOrgPhone.getHeight(); // get height of layout
                    setLayoutsHeight();
                    expanableLayout3.expand();

                    openCheck3 = true;
                } else {
                    expanableLayout3.setExpanded(false);
                    openCheck3 = false;
                }
            }
        });

        lytOrgWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open website in explorer
                String url = "";
                if (placesModel.website != null && !placesModel.website.equals("") && !placesModel.website.equals("null")) {
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

        lytRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent iRouting = new Intent(getContext(), RoutingActivity.class);
//                iRouting.putExtra("PlaceName", placesModel.name);
//                iRouting.putExtra("PlaceLat", placesModel.lat);
//                iRouting.putExtra("PlaceLon", placesModel.lon);
//                //iRouting.putExtra("PlaceType", placesModel.type);
//                iRouting.putExtra("PlaceMainType", 8);
//                startActivity(iRouting);

                showdialog();
            }
        });

        return view;
    }

    private void showdialog() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_navigate);
        Button btnGoogle = (Button) dialog.findViewById(R.id.btnGoogle);
        Button btnInside = (Button) dialog.findViewById(R.id.btnInside);

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = "http://maps.google.com/maps?daddr=" + placesModel.lat + "," + placesModel.lon;

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(address));
                startActivity(intent);
                dialog.dismiss();
            }
        });


        btnInside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iRouting = new Intent(getContext(), RoutingActivity.class);
                iRouting.putExtra("PlaceName", placesModel.name);
                iRouting.putExtra("PlaceLat", placesModel.lat);
                iRouting.putExtra("PlaceLon", placesModel.lon);
                iRouting.putExtra("PlaceType", placesModel.type);
                iRouting.putExtra("PlaceMainType", 8);
                startActivity(iRouting);
                dialog.dismiss();
            }
        });



        dialog.show();
    }

    private void initSlider(View view) {

        mPager = (ViewPager) view.findViewById(R.id.pager);


        mPager.setAdapter(new organizationSliderAdapter(getContext(), imgList));


        CirclePageIndicator indicator = (CirclePageIndicator)
                view.findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

        //Set circle indicator radius
        indicator.setRadius(5 * density);


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
        if (OrganiztionSlider == false) {
            swipeTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 2000, 4000);
            OrganiztionSlider = true;
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
        organizationSlider = (RelativeLayout) view.findViewById(R.id.organizationSlider);
        indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
        lytOrgIntroduce = (LinearLayout) view.findViewById(R.id.lytOrgIntroduce);
        lytOrgAddress = (LinearLayout) view.findViewById(R.id.lytOrgAddress);
        lytOrgPhone = (LinearLayout) view.findViewById(R.id.lytOrgPhone);
        lytOrgWeb = (LinearLayout) view.findViewById(R.id.lytOrgWeb);
        expanableLayout1 = (ExpandableLayout) view.findViewById(R.id.expanableLayout1);
        expanableLayout2 = (ExpandableLayout) view.findViewById(R.id.expanableLayout2);
        expanableLayout3 = (ExpandableLayout) view.findViewById(R.id.expanableLayout3);
        pager = (ViewPagerCustomDuration) view.findViewById(R.id.pager);
        txtOrgintroduce = (TextView) view.findViewById(R.id.txtOrgintroduce);
        txtOrgAddress = (TextView) view.findViewById(R.id.txtOrgAddress);
        imgBack = (ImageView) view.findViewById(R.id.imgBack);
        lytRoute = (LinearLayout) view.findViewById(R.id.lytRoute);
        Recycler = (RecyclerView) view.findViewById(R.id.Recycler);
    }

    private void setUpRecyclerView(List<PhoneModel> list) {

        organizationAdapter adapter = new organizationAdapter(getContext(), list);
        Recycler.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        Recycler.setLayoutManager(mLinearLayoutManagerVertical);
    }

    private void setLayoutsHeight() {
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
        p.weight = 0;
        lytOrgAddress.setLayoutParams(p);
        lytOrgIntroduce.setLayoutParams(p);
        lytOrgPhone.setLayoutParams(p);
        lytOrgWeb.setLayoutParams(p);
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
            placesModel = new PlacesModel();
            imgList = new ArrayList<>();
            databaseHelper = new DatabaseHelper(context);
        }


        @Override
        protected Void doInBackground(Object... objects) {

            placesModel = databaseHelper.selectOrgDetail("Tbl_Offices", 6);

            imgList = databaseHelper.selectPlacesImages(8, placesModel.id);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (imgList != null)
                if (imgList.size() > 0)
                    initSlider(getView());

            if (placesModel != null) {

                txtOrgAddress.setText(placesModel.address);
                txtOrgintroduce.setText(placesModel.info);
            }

        }

    }


    public class DatabaseCallbackPhones extends AsyncTask<Object, Void, Void> {


        private DatabaseHelper databaseHelper;
        private Context context;
//        private String tblName;
//        int id;

        public DatabaseCallbackPhones(Context context) {
            this.context = context;
//            this.tblName = tblName;
//            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            phoneList = new ArrayList<>();
            databaseHelper = new DatabaseHelper(context);
        }


        @Override
        protected Void doInBackground(Object... objects) {

            phoneList = databaseHelper.selectAllPhones();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (phoneList != null) {

               setUpRecyclerView(phoneList);
            }

        }

    }


    @Override
    public void onStop() {
        super.onStop();

        if(databaseCallback != null && databaseCallback.getStatus() == AsyncTask.Status.RUNNING)
            databaseCallback.cancel(true);
        if(CallbackPhones != null && CallbackPhones.getStatus() == AsyncTask.Status.RUNNING)
            CallbackPhones.cancel(true);
    }
}
