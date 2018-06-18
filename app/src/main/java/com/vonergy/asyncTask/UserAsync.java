package com.vonergy.asyncTask;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vonergy.connection.AppSession;
import com.vonergy.connection.ConnectionConstants;
import com.vonergy.connection.Requester;
import com.vonergy.connection.iRequester;
import com.vonergy.model.User;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserAsync extends AsyncTask<Integer, Void, List<User>> {

    private iRequester listener;

    public UserAsync(iRequester listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onTaskStarted();
    }

    @Override
    protected List<User> doInBackground(Integer... params) {
        Gson gson = new Gson();
        String api = "";

        switch (params[0]) {
            case User.login:
                api = ConnectionConstants.login;
                break;
            case User.listUser:
                api = ConnectionConstants.listUser;
                break;
        }

        try {
            List<User> listUser;
            String response;

            if (params[0] == User.login) {
                String jsonString = gson.toJson(AppSession.user, User.class);
                response = new Requester().post(api, jsonString);
                listUser = new ArrayList<>();
                listUser.add(gson.fromJson(response, User.class));
            } else {
                response = new Requester().get(api);
                Type listType = new TypeToken<ArrayList<User>>() {
                }.getType();
                listUser = gson.fromJson(response, listType);
            }
            return listUser;
        } catch (Exception e) {
            e.printStackTrace();
            listener.onTaskFailed(e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<User> userList) {
        super.onPostExecute(userList);
        listener.onTaskCompleted(userList);
    }
}