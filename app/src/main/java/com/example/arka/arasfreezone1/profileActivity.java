package com.example.arka.arasfreezone1;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initView();

        //code for prevent layout moves when keyboard open
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        lytChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPassDialog();
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

                else {
                    // code for change password
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
    }
}
