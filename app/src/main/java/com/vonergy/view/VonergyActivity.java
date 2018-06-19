package com.vonergy.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.vonergy.R;
import com.vonergy.connection.AppSession;
import com.vonergy.model.Consumption;
import com.vonergy.util.Constants;
import com.vonergy.view.fragment.ConsumoDiarioFragment;
import com.vonergy.view.fragment.ConsumoMensalFragment;
import com.vonergy.view.fragment.ConsumoPorHoraFragment;
import com.vonergy.view.fragment.ConsumoTempoRealFragment;
import com.vonergy.view.fragment.ListDeviceFragment;

public class VonergyActivity extends AppCompatActivity {

    ViewPager mViewPager;

    SelectorPageAdapter selectorPageAdapter;
    ListUserActivity mListUserActivity;

    ListDeviceFragment mListDeviceFragment;

    ConsumoTempoRealFragment mConsumoTempoRealFragment;

    ConsumoDiarioFragment mConsumoDiarioFragment;

    ConsumoPorHoraFragment mConsumoPorHoraFragment;

    ConsumoMensalFragment mConsumoMensalFragment;

    private SharedPreferences sharedPreferences;

    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vonergy);

        sharedPreferences = getSharedPreferences(Constants.vonergyPreference, Context.MODE_PRIVATE);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

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
            case R.id.action_list_devices:
                it = new Intent(this, ListDeviceActivity.class);
                startActivity(it);

                return true;

            case R.id.action_list_users:
                it = new Intent(this, ListUserActivity.class);
                startActivity(it);
                return true;

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

    void logout() {
        AppSession.user = null;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.passwordPreference, "");
        editor.apply();
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent it = new Intent(VonergyActivity.this, LoginActivity.class);
                        startActivity(it);
                        finish();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

}

