package com.example.arka.arasfreezone1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.arka.arasfreezone1.adapter.commentsAdapter;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        initView();
        setUpRecyclerView();


        relativeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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
    }

    private void setUpRecyclerView() {

        commentsAdapter adapter = new commentsAdapter(getApplicationContext(), lytCommentReply, btnCloseReply);
        commentsRecycler.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getApplicationContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        commentsRecycler.setLayoutManager(mLinearLayoutManagerVertical);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
    }
}
