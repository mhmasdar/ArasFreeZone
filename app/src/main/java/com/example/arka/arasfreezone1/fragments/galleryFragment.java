package com.example.arka.arasfreezone1.fragments;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.example.arka.arasfreezone1.R;
import com.example.arka.arasfreezone1.app;
import com.example.arka.arasfreezone1.imageActivity;
import com.example.arka.arasfreezone1.services.WebService;

/**
 * A simple {@link Fragment} subclass.
 */
public class galleryFragment extends Fragment {


    private RelativeLayout lytBack;
    private Dialog dialog;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private ImageView img5;
    private ImageView img6;
    private ImageView img7;
    private ImageView img8;
    private ImageView img9;
    private ImageView layout;
    private LinearLayout lytLoading;


    public galleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        initView(view);

        lytBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });


        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity() , imageActivity.class);
//                startActivity(intent);
                showdialog();
            }
        });

        return view;
    }

    private void initView(View view) {
        lytBack = (RelativeLayout) view.findViewById(R.id.lytBack);
        img1 = (ImageView) view.findViewById(R.id.img1);
        img2 = (ImageView) view.findViewById(R.id.img2);
        img3 = (ImageView) view.findViewById(R.id.img3);
        img4 = (ImageView) view.findViewById(R.id.img4);
        img5 = (ImageView) view.findViewById(R.id.img5);
        img6 = (ImageView) view.findViewById(R.id.img6);
        img7 = (ImageView) view.findViewById(R.id.img7);
        img8 = (ImageView) view.findViewById(R.id.img8);
        img9 = (ImageView) view.findViewById(R.id.img9);
    }


    private void showdialog() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_gallery_image);

        layout = (ImageView) dialog.findViewById(R.id.back);
        lytLoading = (LinearLayout) dialog.findViewById(R.id.lytLoading);


        Glide.with(getContext()).load("http://gsharing.ir/Content/Upload/img/Home/13961007.png").asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                // you can do something with loaded bitmap here

                // .....
                layout.setImageBitmap(resource);
                lytLoading.setVisibility(View.GONE);
                layout.setVisibility(View.VISIBLE);
            }
        });


//        DrawableRequestBuilder<String> a = Glide.with(this).load("http://192.168.1.105/Content/files/students/personal/1.png").diskCacheStrategy(DiskCacheStrategy.NONE);
//        String s = String.valueOf(a);
//        int imageResource = getResources().getIdentifier(s, null, getActivity().getPackageName());
//        Drawable res = getResources().getDrawable(imageResource);
//        layout.setImageDrawable(res);

        dialog.show();
    }
}
