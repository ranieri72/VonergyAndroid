package com.vonergy.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.vonergy.R;
import com.vonergy.connection.AppSession;
import com.vonergy.model.Consumption;
import com.vonergy.util.Constants;
import com.vonergy.view.fragment.ChartFragment;
import com.vonergy.view.fragment.GaugeFragment;
import com.vonergy.view.fragment.ListDeviceFragment;
import com.vonergy.view.fragment.ListUserFragment;
import com.vonergy.view.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Fragment fragment = null;
    TextView mName;
    TextView mEmail;
    private SharedPreferences sharedPreferences;
//    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        mName = headerView.findViewById(R.id.txtName);
        mName.setText(AppSession.user.getName());
        mEmail = headerView.findViewById(R.id.txtEmail);
        mEmail.setText(AppSession.user.getEmail());

        showViewSelected(R.id.nav_home);

        sharedPreferences = getSharedPreferences(Constants.vonergyPreference, Context.MODE_PRIVATE);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                break;
            case R.id.action_logout:
                dialogLogout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        showViewSelected(item.getItemId());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void dialogLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.logout));
        builder.setMessage(getResources().getString(R.string.confirmlogout));
        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                logout();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        AlertDialog alerta = builder.create();
        alerta.show();
    }

    private void logout() {
        AppSession.user = null;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.passwordPreference, "");
//        editor.apply();
//        mGoogleSignInClient.signOut()
//                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Intent it = new Intent(MainActivity.this, LoginActivity.class);
//                        startActivity(it);
//                        finish();
//                    }
//                });
    }

    private void showViewSelected(int ItemId) {

        switch (ItemId) {
            case R.id.nav_home:
                fragment = GaugeFragment.newInstance(Consumption.consumptionInRealTime);
                setTitle(R.string.home);
                break;
            case R.id.nav_hourly_history:
                fragment = ChartFragment.newInstance(Consumption.consumptionPerHour);
                setTitle(R.string.hourly_history);
                break;
            case R.id.nav_daily_history:
                fragment = ChartFragment.newInstance(Consumption.dailyConsumption);
                setTitle(R.string.daily_history);
                break;
            case R.id.nav_weekly_history:
                fragment = ChartFragment.newInstance(Consumption.weeklyConsumption);
                setTitle(R.string.weekly_history);
                break;
            case R.id.nav_monthly_history:
                fragment = ChartFragment.newInstance(Consumption.monthlyConsumption);
                setTitle(R.string.monthly_history);
                break;
            case R.id.nav_annual_history:
                fragment = ChartFragment.newInstance(Consumption.annualConsumption);
                setTitle(R.string.annual_history);
                break;
            case R.id.nav_profile:
                fragment = new ProfileFragment();
                setTitle(R.string.profile);
                break;
            case R.id.nav_list_user:
                fragment = new ListUserFragment();
                setTitle(R.string.list_user);
                break;
            case R.id.nav_list_device:
                fragment = new ListDeviceFragment();
                setTitle(R.string.list_device);
                break;
            case R.id.nav_settings:
                Intent it = new Intent(this, ConfigActivity.class);
                startActivity(it);
                break;
            case R.id.nav_logout:
                dialogLogout();
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }
    }
}