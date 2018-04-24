package com.vonergy.asyncTask;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.vonergy.connection.AppSession;
import com.vonergy.connection.Constants;
import com.vonergy.connection.Requester;
import com.vonergy.model.Funcionario;

public class LoginAsync extends AsyncTask<Void, Void, Boolean> {

    private ProgressBar bar;

    public void setProgressBar(ProgressBar bar) {
        this.bar = bar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (bar != null) {
            bar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        Gson gson = new Gson();
        try {
            String jsonString = gson.toJson(AppSession.user, Funcionario.class);
            String response = new Requester().post(Constants.login, jsonString);
            AppSession.user = gson.fromJson(response, Funcionario.class);
            return AppSession.user != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (bar != null) {
            bar.setVisibility(View.GONE);
        }
    }
}