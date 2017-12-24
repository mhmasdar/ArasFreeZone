package com.example.arka.arasfreezone1;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.arka.arasfreezone1.fragments.categorySeatchFragment;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity {


    
    public MapView map;
    public MyLocationNewOverlay mLocationOverlay;
    public CompassOverlay mCompassOverlay;
    public ItemizedIconOverlay locationOverlay;
    public ItemizedIconOverlay currentLocationOverlay;
    public GeoPoint currentLocation;
    public MyLocationListener locationListener;
    public LocationManager locationManager;
    public boolean flagPermission = false;
    private static final String TAG = MainActivity.class.getSimpleName();
    private RelativeLayout lytBack;
    private LinearLayout lytMapTools;
    ArrayList<OverlayItem> items;
    ArrayList<OverlayItem> currentItems;
    private LinearLayout lytDetails;
    private TextView txtName, txtAddress;
    private ImageView imgDetails, imgMyLocation, imgZoomOut, imgZoomIn, imgFilter, imgSort;
    private Dialog filterDialog, sortDialog;
    private Animation mp, mp2, mp3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context ctx = getApplicationContext();
        //important! set your user agent to prevent getting banned from the osm servers
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_map);


        map = (MapView) findViewById(R.id.map);
        lytBack = (RelativeLayout) findViewById(R.id.lytBack);
        lytMapTools = (LinearLayout) findViewById(R.id.lytMapTools);
        lytDetails = (LinearLayout) findViewById(R.id.lytDetails);
        txtName = (TextView) findViewById(R.id.txtName);
        txtAddress = (TextView) findViewById(R.id.txtAddress);
        imgDetails = (ImageView) findViewById(R.id.imgDetails);
        imgMyLocation = (ImageView) findViewById(R.id.imgMyLocation);
        imgZoomOut = (ImageView) findViewById(R.id.imgZoomOut);
        imgZoomIn = (ImageView) findViewById(R.id.imgZoomIn);
        imgFilter = (ImageView) findViewById(R.id.imgFilter);
        imgSort = (ImageView) findViewById(R.id.imgSort);
        map.setTileSource(TileSourceFactory.MAPNIK);


        mp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.map_tool);
        mp2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.map_tool2);
        mp3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash0);

        lytBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        imgZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        imgZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        imgMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFiltersDialog();
            }
        });

        imgSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSortDialog();
            }
        });

        //map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        IMapController mapController = map.getController();
        mapController.setZoom(12);
        GpsMyLocationProvider myLocation = new GpsMyLocationProvider(ctx);
        GeoPoint startPoint1 = new GeoPoint(38.8339216, 45.7146334);
        GeoPoint startPoint2 = new GeoPoint(38.9939216, 45.8146334);
        GeoPoint startPoint3 = new GeoPoint(38.9339216, 45.6146334);
        //GeoPoint startPoint = new GeoPoint(myLocation.getLastKnownLocation().getLatitude(), myLocation.getLastKnownLocation().getLongitude());
        mapController.setCenter(startPoint3);


        this.mLocationOverlay = new MyLocationNewOverlay(myLocation, map);
        this.mLocationOverlay.enableMyLocation();
        //map.getOverlays().add(this.mLocationOverlay);
        //mapController.setCenter(mLocationOverlay.getMyLocation());

        this.mCompassOverlay = new CompassOverlay(ctx, new InternalCompassOrientationProvider(ctx), map);
        this.mCompassOverlay.enableCompass();
        map.getOverlays().add(this.mCompassOverlay);


        // add marker with custom icon ****************************************************************************************

        items = new ArrayList<OverlayItem>();
        OverlayItem myLocationOverlayItem = new OverlayItem("Here1", "Current Position1", startPoint1);
        Drawable myCurrentLocationMarker = this.getResources().getDrawable(R.drawable.marker);
        myLocationOverlayItem.setMarker(myCurrentLocationMarker);

        items.add(myLocationOverlayItem);

        myLocationOverlayItem = new OverlayItem("Here2", "Current Position2", startPoint2);
        myCurrentLocationMarker = this.getResources().getDrawable(R.drawable.marker);
        myLocationOverlayItem.setMarker(myCurrentLocationMarker);

        items.add(myLocationOverlayItem);

        myLocationOverlayItem = new OverlayItem("Here3", "Current Position3", startPoint3);
        myCurrentLocationMarker = this.getResources().getDrawable(R.drawable.marker);
        myLocationOverlayItem.setMarker(myCurrentLocationMarker);

        items.add(myLocationOverlayItem);

        locationOverlay = new ItemizedIconOverlay<OverlayItem>(items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {

                        Glide.with(getApplicationContext()).load(R.drawable.test2).into(imgDetails);
                        lytDetails.setVisibility(View.VISIBLE);
                        lytDetails.startAnimation(mp3);
                        txtName.setText("نام مکان");
                        txtAddress.setText("آدرس مکان");
                        return true;
                    }

                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return true;
                    }
                }, getBaseContext());
        map.getOverlays().add(this.locationOverlay);


        // current location ***********************************************************************************

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            } else {
                flagPermission = true;
            }
        } else {
            flagPermission = true;
        }

        if (flagPermission == true) {
            locationListener = new MyLocationListener();
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                currentLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
                markCurrentLocatin();
            }
        }


        lytMapTools.startAnimation(mp);
        imgFilter.startAnimation(mp2);
        imgSort.startAnimation(mp2);

    }


    public void onResume() {
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
    }


    public class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
            currentLocation = new GeoPoint(location);

            markCurrentLocatin();

//            new Handler().postDelayed(new Runnable() {
//
//            /*
//             * Showing splash screen with a timer. This will be useful when you
//             * want to show case your app logo / company
//             */
//
//                @Override
//                public void run() {
//
//                    markCurrentLocatin();
//
//                }
//            }, 1000);



            //displayMyCurrentLocationOverlay();
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

    private void markCurrentLocatin() {


        currentItems = new ArrayList<>();

        try {
            if (currentLocationOverlay != null)
                if (currentLocationOverlay.size() > 0)
                    currentLocationOverlay.removeAllItems();
        }catch (Exception e){

        }



        OverlayItem myLocationOverlayItemCurrent = new OverlayItem("current", "Current Position", currentLocation);
        Drawable myCurrentLocationMarker = this.getResources().getDrawable(R.drawable.marker_user);
        myLocationOverlayItemCurrent.setMarker(myCurrentLocationMarker);

        currentItems.add(myLocationOverlayItemCurrent);

        currentLocationOverlay = new ItemizedIconOverlay<OverlayItem>(currentItems,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        Toast.makeText(getApplicationContext() , "موقعیت من" , Toast.LENGTH_LONG).show();
                        return true;
                    }

                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return true;
                    }
                }, getBaseContext());
        map.getOverlays().add(this.currentLocationOverlay);


    }

//    private void displayMyCurrentLocationOverlay() {
//        if (currentLocation != null) {
//            if (currentLocationOverlay == null) {
//                currentLocationOverlay = new ItemizedIconOverlay();
//                myCurrentLocationOverlayItem = new OverlayItem(currentLocation, "My Location", "My Location!");
//                currentLocationOverlay.addItem(myCurrentLocationOverlayItem);
//                map.getOverlays().add(currentLocationOverlay);
//            } else {
//                myCurrentLocationOverlayItem.setPoint(currentLocation);
//                currentLocationOverlay.requestRedraw();
//            }
//            map.getController().setCenter(currentLocation);
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
            flagPermission = true;
        } else
            flagPermission = false;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void showFiltersDialog() {

        filterDialog = new Dialog(this);
        filterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        filterDialog.setContentView(R.layout.dialog_filters);
        Button btnFilter = (Button) filterDialog.findViewById(R.id.btnFilter);
        Button btn_cancel = (Button) filterDialog.findViewById(R.id.btn_cancel);
        final CheckBox checkAll = filterDialog.findViewById(R.id.checkAll);
        final CheckBox checkEating = filterDialog.findViewById(R.id.checkEating);
        final CheckBox checkShopping = filterDialog.findViewById(R.id.checkShopping);
        final CheckBox checkStay = filterDialog.findViewById(R.id.checkStay);
        final CheckBox checkTourism = filterDialog.findViewById(R.id.checkTourism);
        final CheckBox checkArt = filterDialog.findViewById(R.id.checkArt);
        final CheckBox checkTransport = filterDialog.findViewById(R.id.checkTransport);
        final CheckBox checkServices = filterDialog.findViewById(R.id.checkServices);
        //final CheckBox checkOffices = filterDialog.findViewById(R.id.checkOffices);
        final CheckBox checkMedical = filterDialog.findViewById(R.id.checkMedical);



        checkEating.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    checkAll.setChecked(false);
            }
        });
        checkShopping.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    checkAll.setChecked(false);
            }
        });
        checkStay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    checkAll.setChecked(false);
            }
        });
        checkTourism.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    checkAll.setChecked(false);
            }
        });
        checkTransport.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    checkAll.setChecked(false);
            }
        });
        checkServices.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    checkAll.setChecked(false);
            }
        });
//        checkOffices.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked)
//                    checkAll.setChecked(false);
//            }
//        });
        checkMedical.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    checkAll.setChecked(false);
            }
        });
        checkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkEating.setChecked(false);
                    checkShopping.setChecked(false);
                    checkStay.setChecked(false);
                    checkTourism.setChecked(false);
                    checkArt.setChecked(false);
                    checkTransport.setChecked(false);
                    checkServices.setChecked(false);
                    //checkOffices.setChecked(false);
                    checkMedical.setChecked(false);

                }
            }
        });


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterDialog.dismiss();
            }
        });


        filterDialog.setCancelable(true);
        filterDialog.setCanceledOnTouchOutside(true);
        filterDialog.show();
    }

    private void showSortDialog() {

        sortDialog = new Dialog(this);
        sortDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sortDialog.setContentView(R.layout.dialog_sort_map);
        Button btnSort = (Button) sortDialog.findViewById(R.id.btnSort);
        Button btn_cancel = (Button) sortDialog.findViewById(R.id.btn_cancel);
        final RadioButton radioRare = sortDialog.findViewById(R.id.radio1);
        final RadioButton radioNear = sortDialog.findViewById(R.id.radio2);
        final RadioButton radioNew = sortDialog.findViewById(R.id.radio3);

        radioRare.setChecked(true);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortDialog.dismiss();
            }
        });

        sortDialog.setCancelable(true);
        sortDialog.setCanceledOnTouchOutside(true);
        sortDialog.show();
    }

}
