package com.vonergy.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.vonergy.R;
import com.vonergy.asyncTask.ConsumptionAsync;
import com.vonergy.model.Consumo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ChartFragment extends Fragment {

    @BindView(R.id.chart)
    LineChart mCharts;

    LineDataSet dataSet;
    LineData lineData;
    Unbinder unbinder;

    public static ChartFragment newInstance(int historyType) {
        ChartFragment f = new ChartFragment();
        Bundle args = new Bundle();
        args.putInt("historyType", historyType);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_chart, container, false);
        unbinder = ButterKnife.bind(this, layout);
        Bundle args = getArguments();
        int historyType = args.getInt("historyType", 0);
        float minValue = Float.MAX_VALUE, maxValue = Float.MIN_VALUE, minKey = Float.MAX_VALUE, maxKey = Float.MIN_VALUE, key, value;
        ArrayList<Entry> entries = new ArrayList<>();

        try {
            List<Consumo> listConsumption = new ConsumptionAsync().execute(historyType).get();

            if (listConsumption != null) {
                for (Consumo consumption : listConsumption) {
                    key = consumption.getRegistrationDate().getTime();
                    value = consumption.getPower();

                    minValue = Math.min(minValue, value);
                    maxValue = Math.max(maxValue, value);
                    minKey = Math.min(minKey, key);
                    maxKey = Math.max(maxKey, key);

                    entries.add(new Entry(key, value));
                }
                if (!entries.isEmpty()) {
                    setValueToChart(entries, minValue, maxValue, minKey, maxKey);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setValueToChart(ArrayList<Entry> entries, float minY, float maxY, float minX, float maxX) {
        dataSet = new LineDataSet(entries, getResources().getString(R.string.consumo));
        lineData = new LineData(dataSet);
        mCharts.setData(lineData);
        dataSet.setColor(getResources().getColor(R.color.colorAccent));
        dataSet.setDrawCircles(false);
        dataSet.setDrawValues(false);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        mCharts.invalidate();
        mCharts.setScaleYEnabled(false);
        mCharts.getDescription().setText("");
        mCharts.getLegend().setEnabled(false);
        mCharts.animateX(2500);

        YAxis leftAxis = mCharts.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setAxisMinimum(minY);
        leftAxis.setAxisMaximum(maxY);

        YAxis rightAxis = mCharts.getAxisRight();
        rightAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        rightAxis.setAxisMinimum(minY);
        rightAxis.setAxisMaximum(maxY);

        XAxis xAxis = mCharts.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setLabelCount(5);
        xAxis.setAxisMinimum(minX);
        xAxis.setAxisMaximum(maxX);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);

        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            private SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return sdf.format(new Date((long) value));
            }
        };
        xAxis.setValueFormatter(formatter);
    }
}