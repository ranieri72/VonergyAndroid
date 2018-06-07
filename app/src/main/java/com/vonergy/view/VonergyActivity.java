package com.vonergy.view;

import android.app.ProgressDialog;
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
import com.vonergy.view.fragment.ConsumoDiarioFragment;
import com.vonergy.view.fragment.ConsumoMensalFragment;
import com.vonergy.view.fragment.ConsumoPorHoraFragment;
import com.vonergy.view.fragment.ConsumoSemanalFragment;
import com.vonergy.view.fragment.ConsumoTempoRealFragment;

public class VonergyActivity extends AppCompatActivity {

    ViewPager mViewPager;

    SelectorPageAdapter selectorPageAdapter;

    ConsumoTempoRealFragment mConsumoTempoRealFragment;

    ConsumoDiarioFragment mConsumoDiarioFragment;

    ConsumoPorHoraFragment mConsumoPorHoraFragment;

    ConsumoSemanalFragment mConsumoSemanalFragment;

    ConsumoMensalFragment mConsumoMensalFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vonergy);

        Toolbar toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


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
                    if (mConsumoSemanalFragment == null) {
                        mConsumoSemanalFragment = ConsumoSemanalFragment.newInstance(Consumption.weeklyConsumption);
                    }
                    return mConsumoSemanalFragment;
                case 4:
                    if (mConsumoMensalFragment == null) {
                        mConsumoMensalFragment = ConsumoMensalFragment.newInstance(Consumption.monthlyConsumption);
                    }
                    return mConsumoMensalFragment;
                default:
                    return mConsumoTempoRealFragment;
            }

        }

        @Override
        public int getCount() {
            return 5;
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
                    return "Semana";
                case 4:
                    return "Mês";
                default:
                    return "";
            }
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
}

