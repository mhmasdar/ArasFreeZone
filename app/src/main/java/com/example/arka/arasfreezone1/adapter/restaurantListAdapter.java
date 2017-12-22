package com.example.arka.arasfreezone1.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.arka.arasfreezone1.MainActivity;
import com.example.arka.arasfreezone1.R;
import com.example.arka.arasfreezone1.app;
import com.example.arka.arasfreezone1.fragments.detailsFragment;
import com.example.arka.arasfreezone1.models.PlacesModel;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mohamadHasan on 30/11/2017.
 */

public class restaurantListAdapter extends RecyclerView.Adapter<restaurantListAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<PlacesModel> placesList;
    private String tblName;

    public restaurantListAdapter(Context context, List<PlacesModel> placesList, String tblName) {
        this.context = context;
        this.placesList = placesList;
        mInflater = LayoutInflater.from(context);
        this.tblName = tblName;

    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_restaurant_list, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        final PlacesModel currentObj = placesList.get(position);
        holder.setData(currentObj, position);

        //holder.rating.setRating(Float.parseFloat("2.0"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tblName.equals("")){
                    getTblName(currentObj.mainType);
                }

                detailsFragment fragment = new detailsFragment();
                Bundle args = new Bundle();
                args.putInt("ID", currentObj.id);
                args.putString("TBL_NAME", tblName);
                fragment.setArguments(args);



                MainActivity activity = (MainActivity) context;
                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container2, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return placesList.size();
    }

    public void getTblName(int type){

        switch (type) {
            case 1:
                tblName = "Tbl_Eating";
                break;
            case 2:
                tblName = "Tbl_Shoppings";
                break;
            case 3:
                tblName = "Tbl_Rests";
                break;
            case 4:
                tblName = "Tbl_Tourisms";
                break;
            case 5:
                tblName = "Tbl_Culturals";
                break;
            case 6:
                tblName = "Tbl_Transports";
                break;
            case 7:
                tblName = "Tbl_Services";
                break;
            case 8:
                tblName = "Tbl_Offices";
                break;
            case 9:
                tblName = "Tbl_Medicals";
                break;
            case 10:
                tblName = "Tbl_Events";
                break;
            default:
                tblName = "";
        }
    }


    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private TextView txtRank;
        private TextView txtAddress;
        private TextView txtType;
        private RatingBar rating;
        private ImageView imgNews;

        int position;
        public PlacesModel current;


        myViewHolder(View itemView) {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtRank = (TextView) itemView.findViewById(R.id.txtRank);
            txtAddress = (TextView) itemView.findViewById(R.id.txtAddress);
            txtType = (TextView) itemView.findViewById(R.id.txtType);
            rating = (RatingBar) itemView.findViewById(R.id.rating);
            imgNews = (ImageView) itemView.findViewById(R.id.imgNews);
            Drawable drawable = rating.getProgressDrawable();
            drawable.setColorFilter(Color.parseColor("#16a086"), PorterDuff.Mode.SRC_ATOP);


        }

        private void setData(PlacesModel current, int position) {

            this.rating.setRating(Float.parseFloat(current.star + ""));
            this.txtName.setText(current.name);
            this.txtAddress.setText(current.address);
            //this.imgNews.setImageResource();
            this.txtRank.setText(current.star + "");
            this.txtType.setText(getPlaceType(current.mainType, current.type));

            this.position = position;
            this.current = current;

        }


    }

    public String getPlaceType(int mainType, int type){

        String returnType = "";

        switch (mainType) {
            case 1:
                tblName = "Tbl_Eating";

                switch (type){
                    case 1:
                        returnType = "رستوران";
                        break;
                    case 2:
                        returnType = "فست فود";
                        break;
                    case 3:
                        returnType = "کافی شاپ";
                        break;
                    case 4:
                        returnType = "شیرینی و آجیل";
                        break;
                    case 5:
                        returnType = "بستنی";
                        break;
                        default:
                }

                break;
            case 2:
                tblName = "Tbl_Shoppings";
                switch (type){
                    case 1:
                        returnType = "مرکز خرید";
                        break;
                    case 2:
                        returnType = "فروشگاه";
                        break;
                    case 3:
                        returnType = "بازارچه";
                        break;
                    default:
                }
                break;
            case 3:
                tblName = "Tbl_Rests";
                switch (type){
                    case 1:
                        returnType = "هتل";
                        break;
                    case 2:
                        returnType = "مسافرخانه";
                        break;
                    case 3:
                        returnType = "خوابگاه";
                        break;
                    default:
                }
                break;
            case 4:
                tblName = "Tbl_Tourisms";
                switch (type){
                    case 1:
                        returnType = "تفریحی";
                        break;
                    case 2:
                        returnType = "میراث فرهنگی";
                        break;
                    case 3:
                        returnType = "پارک";
                        break;
                    case 4:
                        returnType = "جاذبه های طبیعی";
                        break;
                    default:
                }
                break;
            case 5:
                tblName = "Tbl_Culturals";
                switch (type){
                    case 1:
                        returnType = "موزه";
                        break;
                    case 2:
                        returnType = "تئاتر و سینما";
                        break;
                    case 3:
                        returnType = "جشنواره";
                        break;
                    default:
                }
                break;
            case 6:
                tblName = "Tbl_Transports";
                switch (type){
                    case 1:
                        returnType = "تاکسی";
                        break;
                    case 2:
                        returnType = "اتوبوس";
                        break;
                    case 3:
                        returnType = "قطار";
                        break;
                    case 4:
                        returnType = "آژانس تلفنی";
                        break;
                    default:
                }
                break;
            case 7:
                tblName = "Tbl_Services";
                switch (type){
                    case 1:
                        returnType = "سالن ورزشی";
                        break;
                    case 2:
                        returnType = "تعمیر گاه";
                        break;
                    case 3:
                        returnType = "پارکینگ";
                        break;
                    case 4:
                        returnType = "پمپ بنزین";
                        break;
                    case 5:
                        returnType = "مراکز صدور پلاک";
                        break;
                    default:
                }
                break;
            case 8:
                tblName = "Tbl_Offices";
                switch (type){
                    case 1:
                        returnType = "مسجد و امام زاده";
                        break;
                    case 2:
                        returnType = "دانشگاه";
                        break;
                    case 3:
                        returnType = "ادارات";
                        break;
                    case 4:
                        returnType = "کلانتری";
                        break;
                    case 5:
                        returnType = "بانک";
                        break;
                    default:
                }
                break;
            case 9:
                tblName = "Tbl_Medicals";
                switch (type){
                    case 1:
                        returnType = "بیمارستان";
                        break;
                    case 2:
                        returnType = "درمانگاه";
                        break;
                    case 3:
                        returnType = "داروخاه";
                        break;
                    case 4:
                        returnType = "کلینیک";
                        break;
                    default:
                }
                break;
            case 10:
                tblName = "Tbl_Events";
                break;
            default:
                tblName = "";
        }

        return returnType;
    }


}