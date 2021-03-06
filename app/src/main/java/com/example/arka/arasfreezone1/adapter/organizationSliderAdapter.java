package com.example.arka.arasfreezone1.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.arka.arasfreezone1.R;
import com.example.arka.arasfreezone1.app;
import com.example.arka.arasfreezone1.models.ImgModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamadHasan on 25/11/2017.
 */

public class organizationSliderAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<Integer> images = new ArrayList<>();
    List<ImgModel> imageList = new ArrayList<>();

    public organizationSliderAdapter(Context context, List<ImgModel> imageList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
//        images.add(R.drawable.back1);
//        images.add(R.drawable.back2);
//        images.add(R.drawable.back3);
        this.imageList = imageList;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        if (imageList.size() > 3)
            return 3;
        else
            return imageList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View imageLayout = inflater.inflate(R.layout.organization_slider, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.sliderImage);

//        //set image dark
        imageView.setColorFilter(Color.rgb(200, 200, 200), android.graphics.PorterDuff.Mode.MULTIPLY);

        if (imageList.get(position).name != null)
            if (!imageList.get(position).name.equals(""))
                Glide.with(context).load(app.imgMainAddr + app.officeImgAddr + imageList.get(position).name).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);


        view.addView(imageLayout, 0);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //click listener
            }
        });

        return imageLayout;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
