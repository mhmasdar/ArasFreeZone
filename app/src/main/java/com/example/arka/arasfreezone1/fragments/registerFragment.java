package com.example.arka.arasfreezone1.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arka.arasfreezone1.MainActivity;
import com.example.arka.arasfreezone1.R;
import com.example.arka.arasfreezone1.app;
import com.example.arka.arasfreezone1.services.WebService;

/**
 * A simple {@link Fragment} subclass.
 */
public class registerFragment extends Fragment {

    private Button btnRegister;
    private EditText edtName, edtLName, edtMobile, edtEmail, edtPass;

    public registerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        initView(view);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtName.getText().toString().equals("") && !edtLName.getText().toString().equals("") && !edtMobile.getText().toString().equals("") && !edtEmail.getText().toString().equals("") && !edtPass.getText().toString().equals("")) {
                    WebServiceCallBack callBack = new WebServiceCallBack();
                    callBack.execute();
                }
                else{
                    Toast.makeText(getContext(), "لطفا فیلد ها را کامل کنید", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }


    private void initView(View view) {
        edtName = view.findViewById(R.id.edtName);
        edtLName = view.findViewById(R.id.edtLName);
        edtMobile = view.findViewById(R.id.edtMobile);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtPass = view.findViewById(R.id.edtPass);
        btnRegister = view.findViewById(R.id.btnRegister);
    }

    private class WebServiceCallBack extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String result;
        String name, lName, mobile, email, pass;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            name = edtName.getText().toString();
            lName = edtLName.getText().toString();
            mobile = edtMobile.getText().toString();
            email = edtEmail.getText().toString();
            pass = edtPass.getText().toString();
        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.postRegisterInfo(app.isInternetOn(), name, lName, mobile, email, pass);

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
                    editor.putInt("UserId", Integer.parseInt(result));
                    editor.putString("UserName", name);
                    editor.putString("UserLName", lName);
                    editor.putString("UserMobile", mobile);
                    editor.putString("UserEmail", email);
                    editor.putString("UserPass", pass);
                    editor.apply();

//                    Intent i = new Intent(getActivity(), MainActivity.class);
//                    startActivity(i);
                    getActivity().finish();

                }
                else{
                    Toast.makeText(getContext(), "ناموفق", Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(getContext(), "خطا در برقراری ارتباط", Toast.LENGTH_LONG).show();
            }
        }
    }

}
