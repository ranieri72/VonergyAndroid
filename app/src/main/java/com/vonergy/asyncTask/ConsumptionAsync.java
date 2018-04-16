package com.vonergy.asyncTask;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.vonergy.connection.Constants;
import com.vonergy.connection.Requester;
import com.vonergy.model.Consumo;

import java.util.ArrayList;
import java.util.List;

public class ConsumptionAsync extends AsyncTask<Integer, Void, List<Consumo>> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Consumo> doInBackground(Integer... params) {
        Gson gson = new Gson();
        String api = "";

        switch (params[0]) {
            case Consumo.consumptionInRealTime:
                api = Constants.consumptionInRealTime;
                break;
            case Consumo.consumptionPerHour:
                api = Constants.consumptionPerHour;
                break;
            case Consumo.dailyConsumption:
                api = Constants.dailyConsumption;
                break;
            case Consumo.monthlyConsumption:
                api = Constants.monthlyConsumption;
                break;
        }
        try {
            String response = new Requester().get(api);
            List<Consumo> list = new ArrayList<>();
            list.add(gson.fromJson(response, Consumo.class));
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Consumo> listConsumption) {
        super.onPostExecute(listConsumption);
    }
}
