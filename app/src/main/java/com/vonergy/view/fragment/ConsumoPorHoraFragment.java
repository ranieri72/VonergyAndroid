package com.vonergy.view.fragment;


import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.vonergy.R;
import com.vonergy.asyncTask.ConsumptionAsync;
import com.vonergy.connection.iRequester;
import com.vonergy.model.Consumption;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class ConsumoPorHoraFragment extends Fragment implements iRequester {

    private static final String CHAVE_TIPO_CONSUMO = "CHAVE_TIPO_CONSUMO";

    private int tipoConsumo;

    LineChartView mLineChartView;

    ProgressBar mProgressBar;

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
        mProgressBar = layout.findViewById(R.id.progressLoading);
        Bundle args = getArguments();
        tipoConsumo = args.getInt(CHAVE_TIPO_CONSUMO, 0);

        setarGrafico();
        return layout;
    }

    public void setarGrafico() {
        ConsumptionAsync task = new ConsumptionAsync(this);
        task.execute(tipoConsumo);
    }

    @Override
    public void onTaskCompleted(Object o) {
        float key, value;
        key = 0;
        List<PointValue> values = new ArrayList<>();
        List<?> listConsumption = null;
        if (o instanceof List<?>) {
            listConsumption = (List<?>) o;
        }

        if (listConsumption != null && !listConsumption.isEmpty()) {
            for (Object obj : listConsumption) {
                Consumption consumption = (Consumption) obj;
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
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onTaskStarted() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTaskFailed(String errorMessage) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialogError(getResources().getString(R.string.consumptionError));
                }
            });
        }
    }

    private void dialogError(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.error));
        builder.setMessage(msg);

        builder.setNeutralButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        AlertDialog alerta = builder.create();
        alerta.show();
    }
}
