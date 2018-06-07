package com.vonergy.view.fragment;

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
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

public class ConsumoMensalFragment extends Fragment {

    private static final String CHAVE_TIPO_CONSUMO = "CHAVE_TIPO_CONSUMO";

    private int tipoConsumo;

    private ColumnChartView mGraficoMensal;

    private ColumnChartData mData;

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
            List<Column> columns = new ArrayList<Column>();

            List<AxisValue> axisValues = new ArrayList<AxisValue>();

            if (listConsumption != null && !listConsumption.isEmpty()) {
                for (Consumption consumption : listConsumption) {
                    value = consumption.getPower();
                    key++;

                    List<SubcolumnValue> subColumnValues = new ArrayList<SubcolumnValue>();
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

}
