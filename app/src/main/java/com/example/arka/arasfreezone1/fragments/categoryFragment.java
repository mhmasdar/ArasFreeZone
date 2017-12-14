package com.example.arka.arasfreezone1.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.arka.arasfreezone1.R;
import com.example.arka.arasfreezone1.fragments.categories.artFragment;
import com.example.arka.arasfreezone1.fragments.categories.eventsFragment;
import com.example.arka.arasfreezone1.fragments.categories.medicalFragment;
import com.example.arka.arasfreezone1.fragments.categories.officesFragment;
import com.example.arka.arasfreezone1.fragments.categories.organizationFragment;
import com.example.arka.arasfreezone1.fragments.categories.restaurantsListFragment;
import com.example.arka.arasfreezone1.fragments.categories.servicesFragment;
import com.example.arka.arasfreezone1.fragments.categories.shoppingFragment;
import com.example.arka.arasfreezone1.fragments.categories.stayFragment;
import com.example.arka.arasfreezone1.fragments.categories.tourismFragment;
import com.example.arka.arasfreezone1.fragments.categories.transportFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class categoryFragment extends Fragment {


    private RelativeLayout lytMenu;
    private LinearLayout lytStay;
    private LinearLayout lytShopping;
    private LinearLayout lytRestaurant;
    private LinearLayout lytTravel;
    private LinearLayout lytArt;
    private LinearLayout lytTourism;
    private LinearLayout lytMedical;
    private LinearLayout lytOffices;
    private LinearLayout lytUtilities;
    private LinearLayout lytOrganization;
    private LinearLayout lytEvents;
    private int idCategory;


    public categoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        initView(view);


//        Glide.with(this).load(R.drawable.category_back).into(imgCategory);

        lytRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idCategory = 1;
                restaurantsListFragment fragment = new restaurantsListFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container2, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        lytShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idCategory = 2;
                shoppingFragment fragment = new shoppingFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container2, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        lytStay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idCategory = 3;
                stayFragment fragment = new stayFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container2, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        lytTourism.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idCategory = 4;
                tourismFragment fragment = new tourismFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container2, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        lytArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idCategory = 5;
                artFragment fragment = new artFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container2, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        lytTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idCategory = 6;
                transportFragment fragment = new transportFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container2, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        lytUtilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idCategory = 7;
                servicesFragment fragment = new servicesFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container2, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        lytOffices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idCategory = 8;
                officesFragment fragment = new officesFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container2, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        lytMedical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idCategory = 9;

                medicalFragment fragment = new medicalFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container2, fragment);
                ft.addToBackStack(null);
                ft.commit();

            }
        });


        lytEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idCategory = 10;

                eventsFragment fragment = new eventsFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container2, fragment);
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        lytOrganization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                organizationFragment fragment = new organizationFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container2, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return view;
    }

    private void initView(View view) {
        lytMenu = (RelativeLayout) view.findViewById(R.id.lytMenu);
        lytStay = (LinearLayout) view.findViewById(R.id.lytStay);
        lytShopping = (LinearLayout) view.findViewById(R.id.lytShopping);
        lytRestaurant = (LinearLayout) view.findViewById(R.id.lytRestaurant);
        lytTravel = (LinearLayout) view.findViewById(R.id.lytTravel);
        lytArt = (LinearLayout) view.findViewById(R.id.lytArt);
        lytTourism = (LinearLayout) view.findViewById(R.id.lytTourism);
        lytMedical = (LinearLayout) view.findViewById(R.id.lytMedical);
        lytOffices = (LinearLayout) view.findViewById(R.id.lytOffices);
        lytUtilities = (LinearLayout) view.findViewById(R.id.lytUtilities);
        lytOrganization = (LinearLayout) view.findViewById(R.id.lytOrganization);
        lytEvents = (LinearLayout) view.findViewById(R.id.lytEvents);
    }
}
