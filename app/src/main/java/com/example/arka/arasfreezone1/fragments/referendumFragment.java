package com.example.arka.arasfreezone1.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arka.arasfreezone1.R;
import com.example.arka.arasfreezone1.adapter.competitionsAdapter;
import com.example.arka.arasfreezone1.adapter.referendumAdapter;
import com.example.arka.arasfreezone1.adapter.referendumAdapterQuestion;
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
public class referendumFragment extends Fragment {


    private SmoothProgressBar lytLoading;
    private LinearLayout lytMain;
    private RecyclerView recycle;
    private LinearLayout lytQuestionSend;
    private LinearLayout lytRepetitive;
    private LinearLayout lytEmpty;
    private LinearLayout lytDisconnect;
    private TextView txtCompetitionTitle;
    private TextView txtSend;
    private TextView repetitiveTitle;

    private List<ReferendumModel> referendumList;
    private List<Integer> idQuestions;

    private SharedPreferences prefs;
    int idUser, idReferendum;
    boolean isAnsweredRef;

    public referendumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_referendum, container, false);
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
                    if (!prefs.getBoolean("IsAnsweredRef" + idReferendum, false)) {
                        WebServiceCallAnswers callBackAnswer = new WebServiceCallAnswers();
                        callBackAnswer.execute();
                    } else {
                        Toast.makeText(getContext(), "نظر شما برای این نظرسنجی قبلا ثبت شده", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Intent intent = new Intent(getContext(), loginActivity.class);
                    startActivity(intent);

                }
            }
        });

        return view;
    }

    private void initView(View view) {
        lytLoading = (SmoothProgressBar) view.findViewById(R.id.lytLoading);
        lytMain = (LinearLayout) view.findViewById(R.id.lytMain);
        recycle = (RecyclerView) view.findViewById(R.id.recycle);
        lytQuestionSend = (LinearLayout) view.findViewById(R.id.lytQuestionSend);
        lytEmpty = (LinearLayout) view.findViewById(R.id.lytEmpty);
        lytRepetitive = (LinearLayout) view.findViewById(R.id.lytRepetitive);
        lytDisconnect = (LinearLayout) view.findViewById(R.id.lytDisconnect);
        txtCompetitionTitle = (TextView) view.findViewById(R.id.txtCompetitionTitle);
        txtSend = (TextView) view.findViewById(R.id.txtSend);
        repetitiveTitle = view.findViewById(R.id.repetitiveTitle);

    }

    private void setUpRecyclerView(List<ReferendumModel> list) {

        referendumAdapterQuestion adapter = new referendumAdapterQuestion(getContext(), list);
        recycle.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recycle.setLayoutManager(mLinearLayoutManagerVertical);
    }

    private void setUpRecyclerViewResult(List<ReferendumModel> list) {

        referendumAdapter adapter = new referendumAdapter(getContext(), list);
        recycle.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recycle.setLayoutManager(mLinearLayoutManagerVertical);
    }

    private class WebServiceCallBack extends AsyncTask<Object, Void, Void> {

        private WebService webService;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            referendumList = new ArrayList<>();
            idQuestions = new ArrayList<>();
            webService = new WebService();
            lytLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Object... params) {

            referendumList = webService.getReferendoms(app.isInternetOn());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            lytLoading.setVisibility(View.GONE);

            if (referendumList != null) {

                if (referendumList.size() > 0) {
                    txtCompetitionTitle.setText(referendumList.get(0).title);
                    idReferendum = referendumList.get(0).id;
                    for (int i = 0; i < referendumList.size(); i++)
                        idQuestions.add(referendumList.get(i).idQuestion);
                    isAnsweredRef = prefs.getBoolean("IsAnsweredRef" + idReferendum, false);

                    if (idUser > 0) {
                        if (prefs.getBoolean("IsAnsweredRef" + idReferendum, false)) {
                            txtSend.setText("قبلا شرکت کردین");
                            txtCompetitionTitle.setText(referendumList.get(0).title);
                            repetitiveTitle.setText("\"" + referendumList.get(0).title + "\"");
                            lytRepetitive.setVisibility(View.VISIBLE);
                            lytLoading.setVisibility(View.GONE);
                            lytMain.setVisibility(View.VISIBLE);
                            lytEmpty.setVisibility(View.GONE);
                            setUpRecyclerViewResult(referendumList);
                        } else {
                            txtSend.setText("ثبت پاسخ ها");
                            setUpRecyclerView(referendumList);
                            lytMain.setVisibility(View.VISIBLE);
                            lytDisconnect.setVisibility(View.GONE);
                            lytEmpty.setVisibility(View.GONE);

                        }
                    } else {
                        txtSend.setText("ثبت نام/ورود");
                        setUpRecyclerView(referendumList);
                        lytMain.setVisibility(View.VISIBLE);
                        lytDisconnect.setVisibility(View.GONE);
                        lytEmpty.setVisibility(View.GONE);

                    }


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
            answers = referendumAdapterQuestion.answers;
            lytLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.postCompetitionAnswers(app.isInternetOn(), idQuestions, idUser, answers);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            lytLoading.setVisibility(View.GONE);

            if (result != null) {

                if (Integer.parseInt(result) == 1) {

                    Toast.makeText(getContext(), "با موفقیت ثبت شد", Toast.LENGTH_LONG).show();
                    txtSend.setText("قبلا شرکت کرده اید");
//                    txtCompetitionTitle.setText(referendumList.get(0).name);
                    //lytRepetitive.setVisibility(View.GONE);
//                    lytLoading.setVisibility(View.GONE);
//                    lytMain.setVisibility(View.VISIBLE);
//                    lytEmpty.setVisibility(View.GONE);
//                    setUpRecyclerViewResult(referendumList);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("IsAnsweredRef" + idReferendum, true);
                    editor.apply();

                    WebServiceCallBack callBack = new WebServiceCallBack();
                    callBack.execute();

                } else if (Integer.parseInt(result) == 0) {
                    Toast.makeText(getContext(), "ناموفق", Toast.LENGTH_LONG).show();
                } else if (Integer.parseInt(result) == -1) {
                    Toast.makeText(getContext(), "قبلا شرکت کرده اید", Toast.LENGTH_LONG).show();
                    txtSend.setText("قبلا شرکت کرده اید");
                    txtCompetitionTitle.setText(referendumList.get(0).title);
                    repetitiveTitle.setText("\"" + referendumList.get(0).title + "\"");
                    lytRepetitive.setVisibility(View.VISIBLE);
                    lytLoading.setVisibility(View.GONE);
                    lytMain.setVisibility(View.VISIBLE);
                    lytEmpty.setVisibility(View.GONE);
                    setUpRecyclerViewResult(referendumList);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("IsAnsweredRef" + idReferendum, true);
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

//        if (referendumList != null)
//            setUpRecyclerView(referendumList);

        if (idUser > 0) {
            if (idReferendum > 0) {
                if (prefs.getBoolean("IsAnsweredRef" + idReferendum, false)) {
                    txtSend.setText("قبلا شرکت کرده اید");
                    txtCompetitionTitle.setText(referendumList.get(0).title);
                    repetitiveTitle.setText("\"" + referendumList.get(0).title + "\"");
                    //lytRepetitive.setVisibility(View.VISIBLE);
                    lytLoading.setVisibility(View.GONE);
                    lytMain.setVisibility(View.VISIBLE);
                    lytEmpty.setVisibility(View.GONE);
                    setUpRecyclerViewResult(referendumList);
                } else {
                    txtSend.setText("ثبت پاسخ ها");
                    lytMain.setVisibility(View.VISIBLE);
                    lytDisconnect.setVisibility(View.GONE);
                    lytEmpty.setVisibility(View.GONE);

                }
            }
        } else {
            txtSend.setText("ثبت نام/ورود");
            lytMain.setVisibility(View.VISIBLE);
            lytDisconnect.setVisibility(View.GONE);
            lytEmpty.setVisibility(View.GONE);

        }
    }

}
