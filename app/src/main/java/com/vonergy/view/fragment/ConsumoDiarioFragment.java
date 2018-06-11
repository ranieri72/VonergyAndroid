package com.vonergy.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vonergy.R;
import com.vonergy.asyncTask.ConsumptionAsync;
import com.vonergy.model.Consumption;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ConsumoDiarioFragment extends Fragment {

    private static final String CHAVE_TIPO_CONSUMO = "CHAVE_TIPO_CONSUMO";

    private int tipoConsumo;

    public ConsumoDiarioFragment() {
    }

    public static ConsumoDiarioFragment newInstance(int tipoConsumo) {
        ConsumoDiarioFragment fragment = new ConsumoDiarioFragment();
        Bundle args = new Bundle();
        args.putInt(CHAVE_TIPO_CONSUMO, tipoConsumo);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_consumo_diario, container, false);
        Bundle args = getArguments();
        tipoConsumo = args.getInt(CHAVE_TIPO_CONSUMO, 0);

        setarGrafico();
        return layout;
    }

    private void setarGrafico() {

        float key, value;
        key = 0;

        try {
            ConsumptionAsync task = new ConsumptionAsync();
            List<Consumption> listConsumption = task.execute(tipoConsumo).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


}
