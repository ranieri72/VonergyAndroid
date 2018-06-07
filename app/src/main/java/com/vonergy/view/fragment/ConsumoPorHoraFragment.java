package com.vonergy.view.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vonergy.R;
import com.vonergy.asyncTask.ConsumptionAsync;
import com.vonergy.model.Consumption;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class ConsumoPorHoraFragment extends Fragment {

    private static final String CHAVE_TIPO_CONSUMO = "CHAVE_TIPO_CONSUMO";

    private int tipoConsumo;

    LineChartView mLineChartView;

    public ConsumoPorHoraFragment() {
    }


    public static ConsumoPorHoraFragment newInstance(int tipoConsumo) {
        ConsumoPorHoraFragment fragment = new ConsumoPorHoraFragment();
        Bundle args = new Bundle();
        args.putInt(CHAVE_TIPO_CONSUMO, tipoConsumo);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_consumo_por_hora, container, false);
        mLineChartView = layout.findViewById(R.id.graficoPorHora);
        Bundle args = getArguments();
        tipoConsumo = args.getInt(CHAVE_TIPO_CONSUMO, 0);

        setarGrafico();
        return layout;
    }

    public void setarGrafico() {

        try {
            float key, value;
            key = 0;
            ConsumptionAsync task = new ConsumptionAsync();
            List<Consumption> listConsumption = task.execute(tipoConsumo).get();
            List<PointValue> values = new ArrayList<PointValue>();

            if (listConsumption != null && !listConsumption.isEmpty()) {
                for (Consumption consumption : listConsumption) {
                    value = consumption.getPower();
                    key++;

                    values.add(new PointValue(key, value));

                }
                Line line = new Line(values).setColor(Color.GRAY).setCubic(true);
                List<Line> lines = new ArrayList<Line>();
                lines.add(line);

                LineChartData data = new LineChartData();
                data.setLines(lines);

                data = new LineChartData(lines);

                Axis axisX = new Axis();
                Axis axisY = new Axis().setHasLines(true);

                axisX.setName("Hora");
                axisY.setName("Consumo (kwh)");

                data.setAxisXBottom(axisX);
                data.setAxisYLeft(axisY);

                mLineChartView.setLineChartData(data);
            }
        } catch (
                InterruptedException e)

        {
            e.printStackTrace();
        } catch (
                ExecutionException e)

        {
            e.printStackTrace();
        }

    }
}
