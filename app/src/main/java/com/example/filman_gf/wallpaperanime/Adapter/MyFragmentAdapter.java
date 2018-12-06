package com.example.filman_gf.wallpaperanime.Adapter;

//Created By Filman on 12/02/2018

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.filman_gf.wallpaperanime.Fragment.CategoryFragment;
import com.example.filman_gf.wallpaperanime.Fragment.RecentsFragment;
import com.example.filman_gf.wallpaperanime.Fragment.TrendingFragment;

public class MyFragmentAdapter extends FragmentPagerAdapter {

    private Context context;

    public MyFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return CategoryFragment.getInstance();
        else if (position == 1)
            return TrendingFragment.getInstance();
        else if (position == 2)
            return RecentsFragment.getInstance(context);
        else
            return null;

    }

    @Override
    public int getCount() {
        return 3;
    }

    //Ctrl+o

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                return "Best Waifu";
            case 1:
                return "Banyak di lihat";
            case 2:
                return "Baru saja di lihat";
        }
        return "";
    }
}
