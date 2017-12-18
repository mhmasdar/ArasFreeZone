package com.example.arka.arasfreezone1;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.arka.arasfreezone1.db.DatabaseHelper;
import com.example.arka.arasfreezone1.services.WebService;

public class profileActivity extends AppCompatActivity {

    private Dialog dialog;
    private LinearLayout header;
    private RelativeLayout relativeBack;
    private LinearLayout lytChangePassword;
    private LinearLayout lytLogOut;
    private EditText edtFName;
    private EditText edtLName;
    private EditText edtMobile;
    private EditText edtEmail;
    private LinearLayout lytEditInformation;
    private ImageView imgBack;
    SharedPreferences prefs;
    int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initView();

        setViews();

        //code for prevent layout moves when keyboard open
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        lytEditInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtFName.getText().toString().equals("") && !edtLName.getText().toString().equals("") && !edtMobile.getText().toString().equals("") && !edtEmail.getText().toString().equals("")) {
                    WebServiceCallBackProfile callBack = new WebServiceCallBackProfile();
                    callBack.execute();
                }
                else{
                    Toast.makeText(getApplicationContext(), "لطفا فیلد ها را کامل کنید", Toast.LENGTH_LONG).show();
                }
            }
        });

        lytChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPassDialog();
            }
        });

        lytLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(profileActivity.this);
                alert.setTitle("خروج");
                alert.setMessage("آیا مطمئن هستید؟");
                alert.setCancelable(false);
                alert.setIcon(R.drawable.ic_exit);
                alert.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK &&
                                event.getAction() == KeyEvent.ACTION_UP &&
                                !event.isCanceled()) {
                            dialog.cancel();
                            return true;
                        }
                        return false;
                    }
                });
                alert.setPositiveButton("بله", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("LogIn_Check", false);
                        editor.putInt("UserId", -1);
                        editor.putString("UserName", null);
                        editor.putString("UserLName", null);
                        editor.putString("UserMobile", null);
                        editor.putString("UserEmail", null);
                        editor.putString("UserPass", null);
                        editor.apply();

                        DataBaseCallExit callBack = new DataBaseCallExit();
                        callBack.execute();

                        finish();

                    }
                });
                alert.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                alert.create().show();
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
        overridePendingTransition(R.anim.stay, R.anim.activity_back_enter);
    }


    private void showPassDialog() {
        dialog = new Dialog(profileActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_password);
        final EditText edtLastPass = (EditText) dialog.findViewById(R.id.edtLastPass);
        final EditText edtNewPass = (EditText) dialog.findViewById(R.id.edtNewPass);
        Button btnPassSend = (Button) dialog.findViewById(R.id.btnPassSend);
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);


        btnPassSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtLastPass.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), "کلمه عبور فعلی را وارد کنید", Toast.LENGTH_LONG).show();

                else if (edtNewPass.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), "کلمه عبور جدید را وارد کنید", Toast.LENGTH_LONG).show();

                else if (!edtLastPass.getText().toString().equals(prefs.getString("UserPass", "")))
                    Toast.makeText(getApplicationContext(), "کلمه عبور فعلی اشتباه است", Toast.LENGTH_LONG).show();
                else {
                    WebServiceCallBackPass callBackPass = new WebServiceCallBackPass(edtNewPass.getText().toString(), edtLastPass.getText().toString());
                    callBackPass.execute();
                }
            }
        });


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private void initView() {
        header = (LinearLayout) findViewById(R.id.header);
        relativeBack = (RelativeLayout) findViewById(R.id.relative_back);
        lytChangePassword = (LinearLayout) findViewById(R.id.lytChangePassword);
        lytLogOut = (LinearLayout) findViewById(R.id.lytLogOut);
        edtFName = (EditText) findViewById(R.id.edtFName);
        edtLName = (EditText) findViewById(R.id.edtLName);
        edtMobile = (EditText) findViewById(R.id.edtMobile);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        lytEditInformation = (LinearLayout) findViewById(R.id.lytEditInformation);
        imgBack = findViewById(R.id.imgBack);
    }

    public void setViews(){
        prefs = getApplicationContext().getSharedPreferences("MYPREFS", 0);
        idUser = prefs.getInt("UserId", -1);
        edtFName.setText(prefs.getString("UserName", ""));
        edtLName.setText(prefs.getString("UserLName", ""));
        edtMobile.setText(prefs.getString("UserMobile", ""));
        edtEmail.setText(prefs.getString("UserEmail", ""));
    }

    private class WebServiceCallBackProfile extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String result;
        String name, lName, mobile, email, pass;
        int id;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            id = prefs.getInt("UserId", 0);
            name = edtFName.getText().toString();
            lName = edtLName.getText().toString();
            mobile = edtMobile.getText().toString();
            email = edtEmail.getText().toString();
            pass = prefs.getString("UserPass", "");
        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.editProfile(app.isInternetOn(), id, name, lName, mobile, email, pass);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (result != null) {

                if (Integer.parseInt(result) == 1) {

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("LogIn_Check", true);
                    editor.putString("UserName", name);
                    editor.putString("UserLName", lName);
                    editor.putString("UserMobile", mobile);
                    editor.putString("UserEmail", email);
//                    editor.putString("UserPass", pass);
                    editor.apply();

//                    Intent i = new Intent(getActivity(), MainActivity.class);
//                    startActivity(i);
                    finish();

                }
                else{
                    Toast.makeText(getApplicationContext(), "ناموفق", Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(getApplicationContext(), "خطا در برقراری ارتباط", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class WebServiceCallBackPass extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String result;
        String newPass, lastPass;
        int idUser;

        public WebServiceCallBackPass(String newPass, String lastPass){
            this.newPass = newPass;
            this.lastPass = lastPass;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            idUser = prefs.getInt("UserId", 0);
        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.changePass(app.isInternetOn(), idUser, lastPass, newPass);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (result != null) {

                if (Integer.parseInt(result) == 1) {

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("UserPass", newPass);
                    editor.apply();

                    dialog.dismiss();

                }
                else{
                    Toast.makeText(getApplicationContext(), "ناموفق", Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(getApplicationContext(), "خطا در برقراری ارتباط", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class DataBaseCallExit extends AsyncTask<Object, Void, Void> {

        private DatabaseHelper databaseHelper;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            databaseHelper = new DatabaseHelper(getApplicationContext());
        }

        @Override
        protected Void doInBackground(Object... params) {

            databaseHelper.updateTblsAfterExit("Tbl_Eating");
            databaseHelper.updateTblsAfterExit("Tbl_Shoppings");
            databaseHelper.updateTblsAfterExit("Tbl_Rests");
            databaseHelper.updateTblsAfterExit("Tbl_Tourisms");
            databaseHelper.updateTblsAfterExit("Tbl_Culturals");
            databaseHelper.updateTblsAfterExit("Tbl_Transports");
            databaseHelper.updateTblsAfterExit("Tbl_Services");
            databaseHelper.updateTblsAfterExit("Tbl_Medicals");
            databaseHelper.updateTblsAfterExit("Tbl_Events");


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


        }
    }

}
