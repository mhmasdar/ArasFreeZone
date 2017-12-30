package com.example.arka.arasfreezone1.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.arka.arasfreezone1.R;
import com.example.arka.arasfreezone1.app;


/**
 * A simple {@link Fragment} subclass.
 */
public class supportFragment extends Fragment {

    private WebView webView;

    public supportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_support, container, false);
        webView = (WebView) view.findViewById(R.id.webView);
        app.check = 3;

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient(){
                                     @Override
                                     public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                         view.loadUrl(url);
                                         return super.shouldOverrideUrlLoading(view, url);
                                     }
                                 }

        );
        webView.loadUrl("http://gsharing.ir/support/support.html");

//        String customHtml = "<html><body><h1>Hello, WebView</h1></body></html>";
//        webView.loadData(customHtml, "text/html", "UTF-8");
        return view;
    }

}
