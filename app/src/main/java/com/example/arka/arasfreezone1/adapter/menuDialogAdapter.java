package com.example.arka.arasfreezone1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arka.arasfreezone1.R;
import com.example.arka.arasfreezone1.models.MenuModel;

import java.util.List;

/**
 * Created by mohamadHasan on 12/12/2017.
 */

public class menuDialogAdapter extends RecyclerView.Adapter<menuDialogAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<MenuModel> menuList;


    public menuDialogAdapter(Context context, List<MenuModel> menuList) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.menuList = menuList;
    }

    @Override
    public menuDialogAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_menu_list, parent, false);
        menuDialogAdapter.myViewHolder holder = new menuDialogAdapter.myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(menuDialogAdapter.myViewHolder holder, int position) {
        final MenuModel currentObj = menuList.get(position);
        holder.setData(currentObj, position);
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtMenuName;
        private TextView txtMenuPrice;

        int position;
        public MenuModel current;

        myViewHolder(View itemView) {
            super(itemView);

            txtMenuName = (TextView) itemView.findViewById(R.id.txtMenuName);
            txtMenuPrice = (TextView) itemView.findViewById(R.id.txtMenuPrice);

        }

        private void setData(MenuModel current, int position) {

            this.txtMenuName.setText(current.Name);
            this.txtMenuPrice.setText(current.Price);

            this.position = position;
            this.current = current;

        }

    }
}
