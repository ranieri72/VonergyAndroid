package com.vonergy.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vonergy.R;
import com.vonergy.view.fragment.GaugeConfigFragment;
import com.vonergy.view.fragment.MainConfigFragment;

public class ConfigPagerAdapter extends FragmentPagerAdapter {

    private int mNumOfTabs;
    private Context mContext;
    private GaugeConfigFragment mGaugeConfigFragment;
    private MainConfigFragment mMainConfigFragment;

    public ConfigPagerAdapter(Context context, FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.mContext = context;
        mGaugeConfigFragment = new GaugeConfigFragment();
        mMainConfigFragment = new MainConfigFragment();
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return mGaugeConfigFragment;
            case 1:
                return mMainConfigFragment;
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getResources().getString(R.string.history);
            case 1:
                return mContext.getResources().getString(R.string.settings);
            default:
                return null;
        }
    }
}
