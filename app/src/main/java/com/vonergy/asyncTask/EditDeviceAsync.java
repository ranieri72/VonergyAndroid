package com.vonergy.asyncTask;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.vonergy.connection.ConnectionConstants;
import com.vonergy.connection.Requester;
import com.vonergy.model.Device;

public class EditDeviceAsync extends AsyncTask<Device, Void, Device> {

    private ProgressBar bar;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (bar != null) {
            bar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected Device doInBackground(Device... params) {
        Gson gson = new Gson();
        try {
            String jsonString = gson.toJson(params[0], Device.class);
            String response = new Requester().post(ConnectionConstants.editDevice, jsonString);
            return gson.fromJson(response, Device.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Device device) {
        super.onPostExecute(device);
        if (bar != null) {
            bar.setVisibility(View.GONE);
        }
    }
}
