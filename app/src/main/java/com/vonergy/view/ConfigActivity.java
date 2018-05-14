package com.vonergy.view;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.vonergy.R;
import com.vonergy.view.adapter.ConfigPagerAdapter;
import com.vonergy.view.fragment.GaugeConfigFragment;
import com.vonergy.view.fragment.MainConfigFragment;

import butterknife.ButterKnife;

public class ConfigActivity extends AppCompatActivity {

    // Swipe View
    ConfigPagerAdapter mConfigPagerAdapter;
    ViewPager mViewPager;
    GaugeConfigFragment mGaugeConfigFragment;
    MainConfigFragment mMainConfigFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        setTitle(getResources().getString(R.string.settings));
        ButterKnife.bind(this);

        // Swipe View
        mConfigPagerAdapter = new ConfigPagerAdapter(this, getSupportFragmentManager(), 2);
        mGaugeConfigFragment = (GaugeConfigFragment) mConfigPagerAdapter.getItem(0);
        mMainConfigFragment = (MainConfigFragment) mConfigPagerAdapter.getItem(1);
        mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(mConfigPagerAdapter);
    }
}
