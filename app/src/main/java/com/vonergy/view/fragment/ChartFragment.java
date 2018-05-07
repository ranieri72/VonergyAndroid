package com.vonergy.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
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
    BarChart mCharts;

    Unbinder unbinder;
    private int historyType;
    private YAxis leftAxis;
    private YAxis rightAxis;
    private XAxis xAxis;

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
        setChart();
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();

        float minValue = Float.MAX_VALUE, maxValue = Float.MIN_VALUE, minKey = Float.MAX_VALUE, maxKey = Float.MIN_VALUE, key, value;
        ArrayList<BarEntry> entries = new ArrayList<>();

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
            for (int x = 0; x < 224; x++) {
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

                entries.add(new BarEntry(key, value));
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

    private void setChart() {
        mCharts.setDrawBarShadow(false);
        mCharts.setDrawValueAboveBar(true);

        mCharts.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mCharts.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mCharts.setPinchZoom(false);

        mCharts.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);

        mCharts.invalidate();
        mCharts.setScaleYEnabled(false);
        mCharts.getDescription().setText("");
        mCharts.getLegend().setEnabled(false);
        mCharts.animateX(2500);

        leftAxis = mCharts.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);

        rightAxis = mCharts.getAxisRight();
        rightAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);

        xAxis = mCharts.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setLabelCount(5);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);

        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            private SimpleDateFormat sdf = new SimpleDateFormat("dd:MM");

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return sdf.format(new Date((long) value));
            }
        };
        xAxis.setValueFormatter(formatter);
    }

    public void setValueToChart(List<BarEntry> entries, float minY, float maxY, float minX,
                                float maxX) {
        leftAxis.setAxisMinimum(minY);
        leftAxis.setAxisMaximum(maxY);

        rightAxis.setAxisMinimum(minY);
        rightAxis.setAxisMaximum(maxY);

        xAxis.setAxisMinimum(minX);
        xAxis.setAxisMaximum(maxX);

        BarDataSet dataSet = new BarDataSet(entries, getResources().getString(R.string.consumo));
        dataSet.setDrawIcons(false);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        //dataSet.setColor(getResources().getColor(R.color.colorAccent));
        dataSet.setDrawValues(false);
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);

        BarData barData = new BarData(dataSets);
        barData.setValueTextSize(10f);
        barData.setBarWidth(0.9f);
        mCharts.setData(barData);
    }
}