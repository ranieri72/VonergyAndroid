package com.vonergy.view.fragment;

import android.content.DialogInterface;
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
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

public class ConsumoMensalFragment extends Fragment implements iRequester {

    private static final String CHAVE_TIPO_CONSUMO = "CHAVE_TIPO_CONSUMO";

    private int tipoConsumo;

    private ColumnChartView mGraficoMensal;

    private ColumnChartData mData;

    ProgressBar mProgressBar;

    public ConsumoMensalFragment() {
    }

    public static ConsumoMensalFragment newInstance(int tipoConsumo) {
        ConsumoMensalFragment fragment = new ConsumoMensalFragment();
        Bundle args = new Bundle();
        args.putInt(CHAVE_TIPO_CONSUMO, tipoConsumo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_consumo_mensal, container, false);
        mGraficoMensal = layout.findViewById(R.id.graficoPorMes);
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

    private String getNomeMes(int valor) {
        switch (valor) {
            case 0:
                return "Janeiro";
            case 1:
                return "Fevereiro";
            case 2:
                return "Março";
            case 3:
                return "Abril";
            case 4:
                return "Maio";
            case 5:
                return "Junho";
            case 6:
                return "Julho";
            case 7:
                return "Agosto";
            case 8:
                return "Setembro";
            case 9:
                return "Outubro";
            case 10:
                return "Novembro";
            case 11:
                return "Dezembro";
            default:
                return "";
        }
    }

    @Override
    public void onTaskCompleted(Object o) {
        float key, value;
        key = 0;
        List<Column> columns = new ArrayList<>();
        List<AxisValue> axisValues = new ArrayList<>();
        List<?> listConsumption = null;
        if (o instanceof List<?>) {
            listConsumption = (List<?>) o;
        }

        if (listConsumption != null && !listConsumption.isEmpty()) {
            for (Object obj : listConsumption) {
                Consumption consumption = (Consumption) obj;
                value = consumption.getPower();
                key++;

                List<SubcolumnValue> subColumnValues = new ArrayList<>();
                SubcolumnValue subcolumnValue = new SubcolumnValue(value, ChartUtils.pickColor());
                subColumnValues.add(subcolumnValue);

                Column column = new Column(subColumnValues);
                column.setHasLabels(true);
                columns.add(column);

                axisValues.add(new AxisValue(consumption.getId()).setLabel(getNomeMes(Integer.parseInt(String.valueOf(consumption.getId())))));

            }
            mData = new ColumnChartData(columns);
            Axis axisX = new Axis(axisValues);
            Axis axisY = new Axis().setHasLines(true);
            axisX.setName("Mês");
            axisY.setName("Consumo (kwh)");
            mData.setAxisXBottom(axisX);
            mData.setAxisYLeft(axisY);

            mGraficoMensal.setColumnChartData(mData);
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
