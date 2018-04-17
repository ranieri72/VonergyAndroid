package com.vonergy.asyncTask;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.vonergy.connection.Constants;
import com.vonergy.connection.Requester;
import com.vonergy.model.Consumo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ConsumptionAsync extends AsyncTask<Integer, Void, List<Consumo>> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Consumo> doInBackground(Integer... params) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'hh:mm:ss").create();//2018-04-15T18:47:13
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
            List<Consumo> list;
            if (params[0] == Consumo.consumptionInRealTime) {
                Consumo consumption = new Consumo();
                list = new ArrayList<>();
                consumption.setPower(Float.parseFloat(response));
                list.add(consumption);
            } else {
                Type listType = new TypeToken<ArrayList<Consumo>>() {
                }.getType();
                list = gson.fromJson(response, listType);
            }
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
