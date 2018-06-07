package com.vonergy.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.util.Log;

import com.vonergy.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.vonergy.asyncTask.UserAsync;
import com.vonergy.connection.AppSession;
import com.vonergy.connection.ConnectionConstants;
import com.vonergy.model.User;
import com.vonergy.util.Constants;
import com.vonergy.util.Util;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.vonergy.util.Util.refreshedToken;

public class SplashActivity extends AppCompatActivity {

    int progressStatus = 0;

    private Handler handler = new Handler();

    public ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        mProgressBar = findViewById(R.id.progressBarSplash);
        redirect();
    }

    public void redirect(){
        //FirebaseApp.initializeApp(this);
        //refreshedToken = FirebaseApp.getInstance().getToken(false);
        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("Firebase", "Refreshed token: " + refreshedToken);

        final long[] id = {0};

        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(Constants.vonergyPreference, Context.MODE_PRIVATE);
                Util.ipv4 = sharedPreferences.getString(Constants.serverIpPreference, ConnectionConstants.ipv4);

        AppSession.user = new User();
        AppSession.user.setEmail(sharedPreferences.getString(Constants.loginPreference, ""));
        AppSession.user.setPassword(sharedPreferences.getString(Constants.passwordPreference, ""));
        AppSession.user.setFirebaseToken(refreshedToken);

                Intent it = null;

                while(progressStatus < 100){
                    // Update the progress status
                    progressStatus +=1;

                    // Try to sleep the thread for 20 milliseconds
                    try{
                        Thread.sleep(30);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }

                    // Update the progress bar
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(progressStatus);
//                            mFragment.mProgressBar.setProgress(progressStatus);
                            // Show the progress on TextView
                        }
                    });
                }

                try {
                    if (AppSession.user.getEmail().equals("")) {
                        it = new Intent(SplashActivity.this, LoginActivity.class);
                    } else {
                        List<User> listUser =  new UserAsync().execute(User.login).get();
                        if(listUser != null && !listUser.isEmpty()){
                            AppSession.user = listUser.get(0);
                        }
                        if (AppSession.user != null) {
                            it = new Intent(SplashActivity.this, VonergyActivity.class);
                        } else {
                            it = new Intent(SplashActivity.this, LoginActivity.class);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } finally {
                    if (it == null) {
                        it = new Intent(SplashActivity.this, LoginActivity.class);
                    }
                    startActivity(it);
                    finish();
                }
                // Fecha esta activity
                finish();
            }
        }).start(); // Start the operation

    }
}
