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
import com.vonergy.model.Consumo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ChartFragment extends Fragment {

    @BindView(R.id.chart)
    LineChart mCharts;

    //BarChart barChart;

    LineDataSet dataSet;
    LineData lineData;
    Unbinder unbinder;
    private int historyType;

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
        historyType = args.getInt("historyType", 0);
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();

        float minValue = Float.MAX_VALUE, maxValue = Float.MIN_VALUE, minKey = Float.MAX_VALUE, maxKey = Float.MIN_VALUE, key, value;
        ArrayList<Entry> entries = new ArrayList<>();

//        try {
//            ConsumptionAsync task = new ConsumptionAsync();
//            List<Consumo> listConsumption = task.execute(historyType).get();

        List<Consumo> listConsumption = null;
        Consumo consumo;
        String dt = "2018-04-15T18:47:13";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
            Random r = new Random();
            listConsumption = new ArrayList<>();
            for (int x = 0; x < 24; x++) {
                consumo = new Consumo();
                consumo.setRegistrationDate(c.getTime());
                consumo.setPower(r.nextInt(40) + 65);
                listConsumption.add(consumo);
                c.add(Calendar.DATE, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

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
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setValueToChart(ArrayList<Entry> entries, float minY, float maxY, float minX,
                                float maxX) {
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