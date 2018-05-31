package com.vonergy.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.vonergy.asyncTask.UserAsync;
import com.vonergy.connection.AppSession;
import com.vonergy.connection.ConnectionConstants;
import com.vonergy.model.User;
import com.vonergy.util.Constants;
import com.vonergy.util.Util;

import java.util.concurrent.ExecutionException;

import static com.vonergy.util.Util.refreshedToken;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FirebaseApp.initializeApp(this);
        //refreshedToken = FirebaseApp.getInstance().getToken(false);
        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("Firebase", "Refreshed token: " + refreshedToken);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.vonergyPreference, Context.MODE_PRIVATE);
        Util.ipv4 = sharedPreferences.getString(Constants.serverIpPreference, ConnectionConstants.ipv4);

        AppSession.user = new User();
        AppSession.user.setEmail(sharedPreferences.getString(Constants.loginPreference, ""));
        AppSession.user.setPassword(sharedPreferences.getString(Constants.passwordPreference, ""));
        AppSession.user.setFirebaseToken(refreshedToken);

        Intent it = null;
        try {
            if (AppSession.user.getEmail().equals("")) {
                it = new Intent(this, LoginActivity.class);
            } else {
                AppSession.user = new UserAsync().execute(User.login).get().get(0);
                if (AppSession.user != null) {
                    it = new Intent(this, MainActivity.class);
                } else {
                    it = new Intent(this, LoginActivity.class);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            if (it == null) {
                it = new Intent(this, LoginActivity.class);
            }
            startActivity(it);
            finish();
        }
    }
}
