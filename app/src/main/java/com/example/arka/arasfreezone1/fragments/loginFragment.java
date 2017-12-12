package com.example.arka.arasfreezone1.fragments;


import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arka.arasfreezone1.MainActivity;
import com.example.arka.arasfreezone1.R;
import com.example.arka.arasfreezone1.SplashActivity;
import com.example.arka.arasfreezone1.app;
import com.example.arka.arasfreezone1.loginActivity;
import com.example.arka.arasfreezone1.services.WebService;

/**
 * A simple {@link Fragment} subclass.
 */
public class loginFragment extends Fragment {


    private Button btnUserLogin;
    private EditText edtUserName, edtUserPass;
    private TextView txtGuestUser;

    public loginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        initView(view);

        btnUserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtUserName.getText().toString().equals("") && !edtUserPass.getText().toString().equals("")) {
                    WebServiceCallBack callBack = new WebServiceCallBack();
                    callBack.execute();
                }
                else{
                    Toast.makeText(getContext(), "لطفا فیلد ها را کامل کنید", Toast.LENGTH_LONG).show();
                }
            }
        });

        txtGuestUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        return view;
    }


    private void initView(View view) {
        edtUserName = view.findViewById(R.id.edtUserName);
        edtUserPass = view.findViewById(R.id.edtUserPass);
        btnUserLogin = view.findViewById(R.id.btnUserLogin);
        txtGuestUser = view.findViewById(R.id.txtGuestUser);
    }

    private class WebServiceCallBack extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String result;
        String user, pass;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            user = edtUserName.getText().toString();
            pass = edtUserPass.getText().toString();
        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.postLoginInfo(app.isInternetOn(), user, pass);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (result != null) {

                if (Integer.parseInt(result) > 0) {

                    SharedPreferences prefs = getContext().getSharedPreferences("MYPREFS", 0);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("LogIn_Check", true);
                    editor.putString("UserName", user);
                    editor.apply();

                    Intent i = new Intent(getActivity(), MainActivity.class);
                    startActivity(i);
                    getActivity().finish();

                }
                else{
                    Toast.makeText(getContext(), "اشتباه است", Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(getContext(), "خطا در برقراری ارتباط", Toast.LENGTH_LONG).show();
            }
        }
    }

}
