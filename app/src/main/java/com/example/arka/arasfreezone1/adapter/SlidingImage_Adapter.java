package com.example.arka.arasfreezone1.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.arka.arasfreezone1.R;
import com.example.arka.arasfreezone1.models.HomePageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamadHasan on 20/11/2017.
 */

public class SlidingImage_Adapter extends PagerAdapter {


    private LayoutInflater inflater;
    private Context context;
    private List<String> images = new ArrayList<>();
    private String[] names = {"حمام تاریخی کردشت" , "کلیسای سنت استپانوس" , "آسیاب خرابه"};
    private String[] details = {"حمامی قدیمی در شهر جلفا" , "واقع در اطراف شهر" , "منطقه ای بسیار زیبا در شهر جلفا"};
    List<HomePageModel> pageList;

    public SlidingImage_Adapter(Context context, List<HomePageModel> pageList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.pageList = pageList;
        images.add("http://gsharing.ir/Content/Upload/img/Home/13961007.png");
        images.add("http://gsharing.ir/Content/Upload/img/Home/13961007_1.jpg");
        images.add("http://gsharing.ir/Content/Upload/img/Home/13961007_2.jpg");
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.sliderImage);
        final TextView txtSliderName = (TextView) imageLayout
                .findViewById(R.id.txtSliderName);
        final TextView txtSliderDetail = (TextView) imageLayout
                .findViewById(R.id.txtSliderDetail);

        //set image dark
        imageView.setColorFilter(Color.rgb(225, 225, 225), android.graphics.PorterDuff.Mode.MULTIPLY);



        Glide.with(context).load(images.get(position)).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
        txtSliderName.setText(names[position]);
        txtSliderDetail.setText(details[position]);

        view.addView(imageLayout, 0);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //click listener
            }
        });

        return imageLayout;
    }

    public static final Uri getUriToResource(@NonNull Context context,
                                             @AnyRes int resId)
            throws Resources.NotFoundException {
        /** Return a Resources instance for your application's package. */
        Resources res = context.getResources();
        /**
         * Creates a Uri which parses the given encoded URI string.
         * @param uriString an RFC 2396-compliant, encoded URI
         * @throws NullPointerException if uriString is null
         * @return Uri for this given uri string
         */
        Uri resUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + res.getResourcePackageName(resId)
                + '/' + res.getResourceTypeName(resId)
                + '/' + res.getResourceEntryName(resId));
        /** return uri */
        return resUri;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
