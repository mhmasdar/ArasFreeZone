package com.example.arka.arasfreezone1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arka.arasfreezone1.adapter.commentsAdapter;
import com.example.arka.arasfreezone1.models.CommentModel;
import com.example.arka.arasfreezone1.services.WebService;

import java.util.ArrayList;
import java.util.List;

public class commentsActivity extends AppCompatActivity {

    private RelativeLayout relativeBack;
    private RecyclerView commentsRecycler;
    private LinearLayout lytLoginComment;
    private LinearLayout lytSubmitComment;
    private LinearLayout lytSendComments;
    private EditText edtComment;
    private LinearLayout lytMain;
    private LinearLayout lytEmpty;
    private LinearLayout lytDisconnect;
    private LinearLayout lytCommentReply;
    private ImageView btnCloseReply;
    private TextView txtReplyName, txtReplyBody;

    private List<CommentModel> commentList;
    private int mainType;
    private int idRow;

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        idRow = getIntent().getIntExtra("IdRow", 0);
        mainType = getIntent().getIntExtra("MainType", 0);

        initView();


        WebServiceCallBackList webServiceCallList= new WebServiceCallBackList();
        webServiceCallList.execute();

        //setUpRecyclerView();

        relativeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        lytSendComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs = getSharedPreferences("MYPREFS", 0);
                int idUser = prefs.getInt("UserId", -1);

                if (idUser > 0){
                    if (!edtComment.getText().toString().equals("")){
                        WebServiceCallBackAdd callBackAdd = new WebServiceCallBackAdd(idUser);
                        callBackAdd.execute();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "لطفا متن پیام را وارد کنید", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Intent i = new Intent(commentsActivity.this, loginActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fragment_enter, R.anim.fragment_exit);
                }


            }
        });
    }

    private void initView() {
        relativeBack = (RelativeLayout) findViewById(R.id.relative_back);
        commentsRecycler = (RecyclerView) findViewById(R.id.commentsRecycler);
        lytLoginComment = (LinearLayout) findViewById(R.id.lytLoginComment);
        lytSubmitComment = (LinearLayout) findViewById(R.id.lytSubmitComment);
        lytSendComments = (LinearLayout) findViewById(R.id.lytSendComments);
        edtComment = (EditText) findViewById(R.id.edtComment);
        lytMain = (LinearLayout) findViewById(R.id.lytMain);
        lytEmpty = (LinearLayout) findViewById(R.id.lytEmpty);
        lytDisconnect = (LinearLayout) findViewById(R.id.lytDisconnect);
        lytCommentReply = (LinearLayout) findViewById(R.id.lytCommentReply);
        btnCloseReply = (ImageView) findViewById(R.id.btnCloseReply);
        txtReplyName = findViewById(R.id.txtReplyName);
        txtReplyBody = findViewById(R.id.txtReplyBody);
    }

    private void setUpRecyclerView(List<CommentModel> list) {

        commentsAdapter adapter = new commentsAdapter(getApplicationContext(), lytCommentReply, btnCloseReply, txtReplyBody, txtReplyName, list);
        commentsRecycler.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getApplicationContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        commentsRecycler.setLayoutManager(mLinearLayoutManagerVertical);
    }

    private class WebServiceCallBackList extends AsyncTask<Object, Void, Void> {

        private WebService webService;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            commentList = new ArrayList<>();
            webService = new WebService();
        }

        @Override
        protected Void doInBackground(Object... params) {

            commentList = webService.getComments(app.isInternetOn(), idRow, mainType);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (commentList != null) {

                if (commentList.size() > 0) {
                    lytMain.setVisibility(View.VISIBLE);
                    lytDisconnect.setVisibility(View.GONE);
                    lytEmpty.setVisibility(View.GONE);
                    setUpRecyclerView(commentList);
                }
                else {
                    //Toast.makeText(getApplicationContext(), "موردی وجود ندارد", Toast.LENGTH_LONG).show();
                    lytMain.setVisibility(View.GONE);
                    lytDisconnect.setVisibility(View.GONE);
                    lytEmpty.setVisibility(View.VISIBLE);
                }

            } else {
                //Toast.makeText(getApplicationContext(), "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
                lytMain.setVisibility(View.GONE);
                lytDisconnect.setVisibility(View.VISIBLE);
                lytEmpty.setVisibility(View.GONE);
            }

        }

    }

    private class WebServiceCallBackAdd extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String result;
        int idUser;
        String commentBody;

        public WebServiceCallBackAdd(int idUser){
            this.idUser = idUser;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            commentBody = edtComment.getText().toString();
        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.postComment(app.isInternetOn(), idRow, idUser, commentsAdapter.idAnswer, mainType, 0, commentBody, Integer.parseInt(app.getDate()));

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (result != null) {

                if (Integer.parseInt(result) > 0){
                    Toast.makeText(getApplicationContext(), "با موفقیت ثبت شد", Toast.LENGTH_LONG).show();
                    edtComment.setText("");
                    lytCommentReply.setVisibility(View.GONE);
                }
                else{
                    Toast.makeText(getApplicationContext(), "ناموفق", Toast.LENGTH_LONG).show();

                }


            } else {
                Toast.makeText(getApplicationContext(), "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();

            }

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
    }
}
