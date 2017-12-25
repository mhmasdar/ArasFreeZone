package com.example.arka.arasfreezone1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.arka.arasfreezone1.fragments.favoriteFragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class navigationDrawerActivity extends AppCompatActivity {

    private TextView txtLogin;
    private LinearLayout lytIntroduction;
    private LinearLayout lytFavorites;
    private LinearLayout lytreferendum;
    private LinearLayout lytSuggestion;
    private LinearLayout lytShare;
    private LinearLayout lytAbout;
    private LinearLayout lytExit;
    private LinearLayout lytUserInformation;
    private TextView txtUserName;
    private TextView txtUserEmail;
    private ImageView UserImage;
    private ImageView imgBack;
    private ArrayList<Uri> arrayListapkFilepath; // define global

    private SharedPreferences prefs;
    int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        initView();

        prefs = getApplicationContext().getSharedPreferences("MYPREFS", 0);
        txtUserEmail.setText(prefs.getString("UserEmail", "کاربر مهمان"));
        idUser = prefs.getInt("UserId", -1);

        if (idUser > 0)
            txtLogin.setText("مشاهده حساب کاربری");
        else
            txtLogin.setText("ورود / ثبت نام");

        lytSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), suggestionActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.activity_enter, R.anim.stay);
            }
        });


        lytreferendum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), referendumActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.activity_enter, R.anim.stay);

            }
        });


        lytIntroduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), introduceActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.activity_enter, R.anim.stay);
            }
        });

        lytShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //put this code when you wants to share apk
                arrayListapkFilepath = new ArrayList<Uri>();

                shareAPK(getPackageName());
                // you can pass bundle id of installed app in your device instead of getPackageName()
                Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                intent.setType("application/vnd.android.package-archive");
                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM,
                        arrayListapkFilepath);
                startActivity(Intent.createChooser(intent, "Share " +
                        arrayListapkFilepath.size() + " Files Via"));
            }
        });


        lytFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), favoriteActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.activity_enter, R.anim.stay);
            }
        });

        lytAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), aboutActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.activity_enter, R.anim.stay);
            }
        });

        lytUserInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefs = getApplicationContext().getSharedPreferences("MYPREFS", 0);
                idUser = prefs.getInt("UserId", -1);

                if (idUser > 0) {

                    Intent intentProfile = new Intent(getApplicationContext(), profileActivity.class);
                    startActivity(intentProfile);
                    overridePendingTransition(R.anim.activity_enter, R.anim.stay);

                } else {
                    Intent intentLogin = new Intent(getApplicationContext(), loginActivity.class);
                    startActivity(intentLogin);
                    overridePendingTransition(R.anim.activity_enter, R.anim.stay);
                }
            }
        });


        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                prefs = getApplicationContext().getSharedPreferences("MYPREFS", 0);
                idUser = prefs.getInt("UserId", -1);

                if (idUser > 0) {

                    Intent intentProfile = new Intent(getApplicationContext(), profileActivity.class);
                    startActivity(intentProfile);
                    overridePendingTransition(R.anim.activity_enter, R.anim.stay);

                } else {
                    Intent intentLogin = new Intent(getApplicationContext(), loginActivity.class);
                    startActivity(intentLogin);
                    overridePendingTransition(R.anim.activity_enter, R.anim.stay);
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.top_to_bottom);
    }

    private void initView() {
        txtLogin = (TextView) findViewById(R.id.txtLogin);
        lytIntroduction = (LinearLayout) findViewById(R.id.lytIntroduction);
        lytFavorites = (LinearLayout) findViewById(R.id.lytFavorites);
        lytreferendum = (LinearLayout) findViewById(R.id.lytreferendum);
        lytSuggestion = (LinearLayout) findViewById(R.id.lytSuggestion);
        lytShare = (LinearLayout) findViewById(R.id.lytShare);
        lytAbout = (LinearLayout) findViewById(R.id.lytAbout);
        lytExit = (LinearLayout) findViewById(R.id.lytExit);
        lytUserInformation = (LinearLayout) findViewById(R.id.lytUserInformation);
        txtUserName = (TextView) findViewById(R.id.txtUserName);
        txtUserEmail = (TextView) findViewById(R.id.txtUserEmail);
        UserImage = (ImageView) findViewById(R.id.UserImage);
        imgBack = (ImageView) findViewById(R.id.imgBack);
    }

    @Override
    protected void onResume() {
        super.onResume();

        txtUserEmail.setText(prefs.getString("UserEmail", "کاربر مهمان"));
        idUser = prefs.getInt("UserId", -1);

        if (idUser > 0) {
            txtLogin.setText("مشاهده حساب کاربری");
        }
        else{
            txtLogin.setText("ورود / ثبت نام");
        }

    }



    private void shareAPK(String bundle_id) {
        File f1;
        File f2 = null;

        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List pkgAppsList = getPackageManager().queryIntentActivities(mainIntent, 0);
        int z = 0;
        for (Object object : pkgAppsList) {

            ResolveInfo info = (ResolveInfo) object;
            if (info.activityInfo.packageName.equals(bundle_id)) {

                f1 = new File(info.activityInfo.applicationInfo.publicSourceDir);

                try {

                    String file_name = info.loadLabel(getPackageManager()).toString();


                    f2 = new File(Environment.getExternalStorageDirectory().toString() + "/Folder");
                    f2.mkdirs();
                    f2 = new File(f2.getPath() + "/" + file_name + ".apk");
                    f2.createNewFile();

                    InputStream in = new FileInputStream(f1);

                    OutputStream out = new FileOutputStream(f2);

                    // byte[] buf = new byte[1024];
                    byte[] buf = new byte[4096];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();
                    System.out.println("File copied.");
                } catch (FileNotFoundException ex) {
                    System.out.println(ex.getMessage() + " in the specified directory.");
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        arrayListapkFilepath.add(Uri.fromFile(new File(f2.getAbsolutePath())));

    }
}
