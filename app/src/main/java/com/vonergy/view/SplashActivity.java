package com.vonergy.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.iid.FirebaseInstanceId;
import com.vonergy.R;
import com.vonergy.asyncTask.UserAsync;
import com.vonergy.connection.AppSession;
import com.vonergy.connection.ConnectionConstants;
import com.vonergy.connection.iRequester;
import com.vonergy.model.User;
import com.vonergy.util.Constants;
import com.vonergy.util.Util;

import java.util.List;

import static com.vonergy.connection.AppSession.user;
import static com.vonergy.util.Util.refreshedToken;

public class SplashActivity extends AppCompatActivity implements iRequester {

    public ProgressBar mProgressBar;

    Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        mProgressBar = findViewById(R.id.progressLoading);
        redirect();
    }

    public void redirect() {
        Util.refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("Firebase", "Refreshed token: " + refreshedToken);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.vonergyPreference, Context.MODE_PRIVATE);
        Util.ipv4 = sharedPreferences.getString(Constants.serverIpPreference, ConnectionConstants.ipv4);

        User user = new User();
        user.setEmail(sharedPreferences.getString(Constants.loginPreference, ""));
        user.setPassword(sharedPreferences.getString(Constants.passwordPreference, ""));
        user.setFirebaseToken(refreshedToken);

        if (user.getEmail().equals("") || user.getPassword().equals("")) {
            it = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(it);
            finish();
        } else {
            AppSession.user = user;
            new UserAsync(this).execute(User.login);
        }
    }

    @Override
    public void onTaskCompleted(Object o) {
        mProgressBar.setVisibility(View.GONE);
        List<?> listUser = null;
        if (o instanceof List<?>) {
            listUser = (List<?>) o;
        }

        if (listUser != null && !listUser.isEmpty()) {
            user = (User) listUser.get(0);
        }
        if (user != null) {
            it = new Intent(SplashActivity.this, VonergyActivity.class);
        } else {
            it = new Intent(SplashActivity.this, LoginActivity.class);
        }
        startActivity(it);
        finish();
    }

    @Override
    public void onTaskStarted() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTaskFailed(String errorMessage) {
        mProgressBar.setVisibility(View.GONE);
        it = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(it);
        finish();
    }
}
