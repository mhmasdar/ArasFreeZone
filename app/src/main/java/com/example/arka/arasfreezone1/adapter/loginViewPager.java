package com.example.arka.arasfreezone1.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.arka.arasfreezone1.fragments.competitionFragment;
import com.example.arka.arasfreezone1.fragments.loginFragment;
import com.example.arka.arasfreezone1.fragments.referendumFragment;
import com.example.arka.arasfreezone1.fragments.registerFragment;

/**
 * Created by mohamadHasan on 08/12/2017.
 */

public class loginViewPager extends FragmentStatePagerAdapter {

    public loginViewPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                loginFragment RMsg = new loginFragment();
                return RMsg;
            case 1:
                registerFragment SMsg = new registerFragment();
                return SMsg;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

}