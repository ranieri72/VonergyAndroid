package com.vonergy.asyncTask;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vonergy.connection.ConnectionConstants;
import com.vonergy.connection.Requester;
import com.vonergy.connection.iRequester;
import com.vonergy.model.Device;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DeviceAsync extends AsyncTask<Void, Void, List<Device>> {

    private iRequester listener;

    public DeviceAsync(iRequester listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onTaskStarted();
    }

    @Override
    protected List<Device> doInBackground(Void... params) {
        Gson gson = new Gson();

        try {
            String response = new Requester().get(ConnectionConstants.listDevice);
            Type listType = new TypeToken<ArrayList<Device>>() {
            }.getType();
            return gson.fromJson(response, listType);
        } catch (Exception e) {
            e.printStackTrace();
            listener.onTaskFailed(e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Device> listDevice) {
        super.onPostExecute(listDevice);
        listener.onTaskCompleted(listDevice);
    }
}
