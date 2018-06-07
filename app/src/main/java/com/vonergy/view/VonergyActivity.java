package com.vonergy.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.vonergy.R;
import com.vonergy.model.Consumption;
import com.vonergy.util.Constants;
import com.vonergy.view.fragment.ConsumoDiarioFragment;
import com.vonergy.view.fragment.ConsumoMensalFragment;
import com.vonergy.view.fragment.ConsumoPorHoraFragment;
import com.vonergy.view.fragment.ConsumoTempoRealFragment;
import com.vonergy.view.fragment.ProfileFragment;

public class VonergyActivity extends AppCompatActivity {

    ViewPager mViewPager;

    SelectorPageAdapter selectorPageAdapter;

    ConsumoTempoRealFragment mConsumoTempoRealFragment;

    ConsumoDiarioFragment mConsumoDiarioFragment;

    ConsumoPorHoraFragment mConsumoPorHoraFragment;

    ConsumoMensalFragment mConsumoMensalFragment;

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vonergy);

        Toolbar toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        sharedPreferences = getSharedPreferences(Constants.vonergyPreference, Context.MODE_PRIVATE);


        buildViewPager();


    }

    private void buildViewPager() {
        mViewPager = findViewById(R.id.viewPager);
        selectorPageAdapter = new SelectorPageAdapter(getSupportFragmentManager());
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(selectorPageAdapter);

        TabLayout tab = findViewById(R.id.tabs);
        tab.setupWithViewPager(mViewPager);
    }

    public class SelectorPageAdapter extends FragmentPagerAdapter {

        public SelectorPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (mConsumoTempoRealFragment == null) {
                        mConsumoTempoRealFragment = ConsumoTempoRealFragment.newInstance(Consumption.consumptionInRealTime);
                    }
                    return mConsumoTempoRealFragment;
                case 1:
                    if (mConsumoPorHoraFragment == null) {
                        mConsumoPorHoraFragment = ConsumoPorHoraFragment.newInstance(Consumption.consumptionPerHour);
                    }
                    return mConsumoPorHoraFragment;
                case 2:
                    if (mConsumoDiarioFragment == null) {
                        mConsumoDiarioFragment = ConsumoDiarioFragment.newInstance(Consumption.dailyConsumption);
                    }
                    return mConsumoDiarioFragment;
                case 3:
                    if (mConsumoMensalFragment == null) {
                        mConsumoMensalFragment = ConsumoMensalFragment.newInstance(Consumption.monthlyConsumption);
                    }
                    return mConsumoMensalFragment;
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Real";
                case 1:
                    return "Hora";
                case 2:
                    return "Dia";
                case 3:
                    return "Mês";
                default:
                    return "";
            }
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent it;
        switch (item.getItemId()) {
            case R.id.action_settings:
                it = new Intent(this, SettingActivity.class);
                startActivity(it);
                return true;
            case R.id.action_profile:
                it = new Intent(this, ProfileActivity.class);
                startActivity(it);
                return true;
            case R.id.action_sair:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void logout(){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.passwordPreference, "");
        editor.putString(Constants.loginPreference, "");

        Intent it = new Intent(this, LoginActivity.class);
        startActivity(it);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
}

