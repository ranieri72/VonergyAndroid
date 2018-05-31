package com.vonergy.asyncTask;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vonergy.connection.ConnectionConstants;
import com.vonergy.connection.Requester;
import com.vonergy.model.Device;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DeviceAsync extends AsyncTask<Void, Void, List<Device>> {

    private ProgressBar bar;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (bar != null) {
            bar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected List<Device> doInBackground(Void... params) {
        Gson gson = new Gson();

        try {
            String response = new Requester().get(ConnectionConstants.device);
            Type listType = new TypeToken<ArrayList<Device>>() {
            }.getType();
            return gson.fromJson(response, listType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Device> listDevice) {
        super.onPostExecute(listDevice);
        if (bar != null) {
            bar.setVisibility(View.GONE);
        }
    }

    public void setProgressBar(ProgressBar bar) {
        this.bar = bar;
    }
}
