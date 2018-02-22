package com.example.arka.arasfreezone1;

import android.*;
import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arka.arasfreezone1.services.WebService;

public class FreeNetActivity extends AppCompatActivity {

    private RelativeLayout relativeBack;
    private Button btnFreeNet;
    SharedPreferences prefs;
    public boolean permissionState, permissionLoc;

    Dialog dialog2;

    int idUser;
    String IMEI = "0", macAddress, email;

    CallBackFreeNet callBackFreeNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_net);
        initView();

        relativeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        btnFreeNet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs = getSharedPreferences("MYPREFS", 0);
                idUser = prefs.getInt("UserId", -1);
                if (idUser > 0) {

                    if (!isReadStateAllowed()) {
                        requestStoragePermission();
                    } else {
                        callBackFreeNet = new CallBackFreeNet();
                        callBackFreeNet.execute();
                    }

                } else {
                    Snackbar snackbar = Snackbar.make(v, "ابتدا باید ثبت نام کنید", Snackbar.LENGTH_LONG);
                    snackbar.setAction("ثبت نام", new registerAction());

                    Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
                    TextView textView = (TextView) layout.findViewById(android.support.design.R.id.snackbar_text);
                    LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.5f);
                    textView.setLayoutParams(parms);
                    textView.setGravity(Gravity.LEFT);
                    snackbar.setActionTextColor(getResources().getColor(R.color.yellow));
                    snackbar.show();
                }
            }
        });

    }

    public class registerAction implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            Intent i = new Intent(FreeNetActivity.this, loginActivity.class);
            startActivity(i);
        }
    }

    private void initView() {
        relativeBack = (RelativeLayout) findViewById(R.id.relativeBack);
        btnFreeNet = (Button) findViewById(R.id.btnFreeNet);
    }


    //permission for imei and mac address

    private boolean isReadStateAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE);
        int resultLoc = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED && resultLoc == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    private void requestStoragePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_PHONE_STATE)) {
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_PHONE_STATE}, 23);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 24);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 23) {
            //Getting the permission status
            int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE);
            int resultLoc = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (result == PackageManager.PERMISSION_GRANTED && resultLoc == PackageManager.PERMISSION_GRANTED) {

                callBackFreeNet = new CallBackFreeNet();
                callBackFreeNet.execute();
            } else {
                Toast.makeText(getApplicationContext(), "دسترسی ها صادر نشده است", Toast.LENGTH_LONG).show();
            }
        }
    }


    private class CallBackFreeNet extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();

            dialog2 = new Dialog(FreeNetActivity.this);
            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog2.setContentView(R.layout.dialog_waiting);
            dialog2.setCancelable(true);
            dialog2.setCanceledOnTouchOutside(true);
            dialog2.show();


            TelephonyManager telephonyManager = (TelephonyManager) app.context.getSystemService(app.context.TELEPHONY_SERVICE);
            IMEI = telephonyManager.getDeviceId();
//                Toast.makeText(getContext() , "sent" , Toast.LENGTH_SHORT).show();

            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wInfo = wifiManager.getConnectionInfo();
            macAddress = wInfo.getMacAddress();

            prefs = getSharedPreferences("MYPREFS", 0);
            email = prefs.getString("UserEmail", "");

        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.freeNet(app.isInternetOn(), email, IMEI, macAddress);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dialog2.dismiss();

            if (result != null) {

                if (result.equals("true")) {

                    Toast.makeText(getApplicationContext(), "درخواست با موفقیت ثبت شد", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(), "ناموفق", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(getApplicationContext(), "ارتباط با سرور برقرار نشد", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        prefs = getSharedPreferences("MYPREFS", 0);
        idUser = prefs.getInt("UserId", -1);
    }

    @Override
    public void onStop() {
        super.onStop();
//        Toast.makeText(getContext(), "stop", Toast.LENGTH_LONG).show();
        if (callBackFreeNet != null)
            if (callBackFreeNet.getStatus() == AsyncTask.Status.RUNNING)
                callBackFreeNet.cancel(true);
    }

}
