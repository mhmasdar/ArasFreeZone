package com.example.arka.arasfreezone1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class suggestionActivity extends AppCompatActivity {

    private RelativeLayout relativeBack;
    private EditText edtName;
    private EditText edtEmail;
    private EditText edtTitle;
    private EditText edtBody;
    private Button btnSendSuggestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        initView();
    }

    private void initView() {
        relativeBack = (RelativeLayout) findViewById(R.id.relative_back);
        edtName = (EditText) findViewById(R.id.edtName);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtTitle = (EditText) findViewById(R.id.edtTitle);
        edtBody = (EditText) findViewById(R.id.edtBody);
        btnSendSuggestion = (Button) findViewById(R.id.btnSendSuggestion);

        relativeBack.setOnClickListener(new View.OnClickListener() {
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
}
