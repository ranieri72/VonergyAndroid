package com.vonergy.asyncTask;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.vonergy.connection.ConnectionConstants;
import com.vonergy.connection.Requester;
import com.vonergy.connection.iRequester;
import com.vonergy.model.Device;

public class EditDeviceAsync extends AsyncTask<Device, Void, Device> {

    private iRequester listener;

    public EditDeviceAsync(iRequester listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onTaskStarted();
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
            listener.onTaskFailed(e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(Device device) {
        super.onPostExecute(device);
        listener.onTaskCompleted(device);
    }
}
