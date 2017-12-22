package com.example.arka.arasfreezone1.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arka.arasfreezone1.R;
import com.example.arka.arasfreezone1.adapter.competitionsAdapter;
import com.example.arka.arasfreezone1.app;
import com.example.arka.arasfreezone1.loginActivity;
import com.example.arka.arasfreezone1.models.ReferendumModel;
import com.example.arka.arasfreezone1.services.WebService;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class competitionFragment extends Fragment {

    private SmoothProgressBar lytLoading;
    private RecyclerView recycle;
    private LinearLayout lytQuestionSend;
    private LinearLayout lytMain;
    private LinearLayout lytEmpty;
    private LinearLayout lytDisconnect;
    private TextView txtCompetitionTitle, txtSend;

    private List<ReferendumModel> referendumList;

    private SharedPreferences prefs;
    int idUser, idRef;
    boolean isAnswered;

    public competitionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_competition, container, false);
        initView(view);

        prefs = getContext().getSharedPreferences("MYPREFS", 0);
        idUser = prefs.getInt("UserId", -1);

        WebServiceCallBack webServiceCallBack = new WebServiceCallBack();
        webServiceCallBack.execute();


        //setUpRecyclerView();

        lytQuestionSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idUser > 0) {
                    if (!prefs.getBoolean("IsAnswered" + idRef, false)) {
                        WebServiceCallAnswers callBackAnswer = new WebServiceCallAnswers();
                        callBackAnswer.execute();
                    }
                } else {
                    Intent intent = new Intent(getContext(), loginActivity.class);
                    startActivity(intent);

                }
            }
        });

        return view;
    }

    private void setUpRecyclerView(List<ReferendumModel> list) {

        competitionsAdapter adapter = new competitionsAdapter(getContext(), list);
        recycle.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recycle.setLayoutManager(mLinearLayoutManagerVertical);
    }

    private void initView(View view) {
        recycle = (RecyclerView) view.findViewById(R.id.recycle);
        lytQuestionSend = (LinearLayout) view.findViewById(R.id.lytQuestionSend);
        lytMain = (LinearLayout) view.findViewById(R.id.lytMain);
        lytEmpty = (LinearLayout) view.findViewById(R.id.lytEmpty);
        lytDisconnect = (LinearLayout) view.findViewById(R.id.lytDisconnect);
        txtCompetitionTitle = view.findViewById(R.id.txtCompetitionTitle);
        txtSend = view.findViewById(R.id.txtSend);
        lytLoading = view.findViewById(R.id.lytLoading);
    }

    private class WebServiceCallBack extends AsyncTask<Object, Void, Void> {

        private WebService webService;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            referendumList = new ArrayList<>();
            webService = new WebService();
        }

        @Override
        protected Void doInBackground(Object... params) {

            referendumList = webService.getComptitions(app.isInternetOn());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (referendumList != null) {

                if (referendumList.size() > 0) {
                    txtCompetitionTitle.setText(referendumList.get(0).title);
                    idRef = referendumList.get(0).id;
                    isAnswered = prefs.getBoolean("IsAnswered" + idRef, false);

                    if (idUser > 0) {
                        if (prefs.getBoolean("IsAnswered" + idRef, false))
                            txtSend.setText("قبلا شرکت کرده اید");
                        else
                            txtSend.setText("ثبت پاسخ ها");
                    } else {
                        txtSend.setText("ثبت نام/ورود");
                    }


                    setUpRecyclerView(referendumList);
                    lytMain.setVisibility(View.VISIBLE);
                    lytDisconnect.setVisibility(View.GONE);
                    lytEmpty.setVisibility(View.GONE);

                } else if (referendumList.size() < 1) {
                    lytMain.setVisibility(View.GONE);
                    lytDisconnect.setVisibility(View.GONE);
                    lytEmpty.setVisibility(View.VISIBLE);
                }

            } else {
                lytMain.setVisibility(View.GONE);
                lytDisconnect.setVisibility(View.VISIBLE);
                lytEmpty.setVisibility(View.GONE);
            }

        }

    }

    private class WebServiceCallAnswers extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        List<Integer> answers;
        String result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            answers = competitionsAdapter.answers;
        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.postCompetitionAnswers(app.isInternetOn(), idRef, idUser, answers);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (result != null) {

                if (Integer.parseInt(result) == 1) {

                    Toast.makeText(getContext(), "با موفقیت ثبت شد", Toast.LENGTH_LONG).show();
                    txtSend.setText("قبلا شرکت کرده اید");
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("IsAnswered" + idRef, true);
                    editor.apply();

                } else if (Integer.parseInt(result) == 0) {
                    Toast.makeText(getContext(), "ناموفق", Toast.LENGTH_LONG).show();
                } else if (Integer.parseInt(result) == -1) {
                    Toast.makeText(getContext(), "قبلا شرکت کرده اید", Toast.LENGTH_LONG).show();
                    txtSend.setText("قبلا شرکت کرده اید");
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("IsAnswered" + idRef, true);
                    editor.apply();
                }


            } else {
                Toast.makeText(getContext(), "مشکل در ارتباط با سرور", Toast.LENGTH_LONG).show();
            }

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        prefs = getContext().getSharedPreferences("MYPREFS", 0);
        idUser = prefs.getInt("UserId", -1);

        if (idUser > 0) {
            if (idRef > 0) {
                if (prefs.getBoolean("IsAnswered" + idRef, false))
                    txtSend.setText("قبلا شرکت کرده اید");
                else
                    txtSend.setText("ثبت پاسخ ها");
            }
        } else {
            txtSend.setText("ثبت نام/ورود");
        }
    }
}
