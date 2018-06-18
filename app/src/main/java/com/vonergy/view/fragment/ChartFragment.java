package com.vonergy.view.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.vonergy.connection.iRequester;
import com.vonergy.model.Consumption;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.vonergy.model.Consumption.kWhCost;

public class ChartFragment extends Fragment implements iRequester {

    @BindView(R.id.chart)
    LineChart mCharts;

    @BindView(R.id.cost)
    TextView mCost;

    @BindView(R.id.indeterminateBar)
    ProgressBar mProgressBar;

    //BarChart mCharts;

    Unbinder unbinder;
    private int historyType;
    private String dateFormat;
    private int multiplier;

    //private YAxis leftAxis;
    //private YAxis rightAxis;
    //private XAxis xAxis;

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

        switch (historyType) {
            case Consumption.consumptionPerHour:
                dateFormat = "hh:mm";
                multiplier = 1;
                break;
            case Consumption.dailyConsumption:
                dateFormat = "dd-MM-yy";
                multiplier = 24;
                break;
            case Consumption.weeklyConsumption:
                dateFormat = "dd-MM-yy";
                multiplier = 168;
                break;
            case Consumption.monthlyConsumption:
                dateFormat = "MMM-yy";
                multiplier = 720;
                break;
            case Consumption.annualConsumption:
                dateFormat = "yyyy";
                multiplier = 8760;
                break;
        }
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        ConsumptionAsync task = new ConsumptionAsync(this);
        task.execute(historyType);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setValueToChart(ArrayList<Entry> entries, float minY, float maxY, float minX, float maxX) {
        LineDataSet dataSet = new LineDataSet(entries, getResources().getString(R.string.consumo));
        LineData lineData = new LineData(dataSet);
        mCharts.setData(lineData);
        dataSet.setColor(getResources().getColor(R.color.black));
        dataSet.setDrawCircles(false);
        dataSet.setDrawValues(false);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        mCharts.invalidate();
        mCharts.setScaleYEnabled(false);
        mCharts.getDescription().setText("");
        mCharts.getLegend().setEnabled(false);
        mCharts.animateX(2000);

        YAxis leftAxis = mCharts.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setAxisMinimum(minY);
        leftAxis.setAxisMaximum(maxY);
        leftAxis.setTextColor(getResources().getColor(R.color.black));

        YAxis rightAxis = mCharts.getAxisRight();
        rightAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        rightAxis.setAxisMinimum(minY);
        rightAxis.setAxisMaximum(maxY);
        rightAxis.setTextColor(getResources().getColor(R.color.black));

        XAxis xAxis = mCharts.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setLabelCount(5);
        xAxis.setAxisMinimum(minX);
        xAxis.setAxisMaximum(maxX);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setTextColor(getResources().getColor(R.color.black));

        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            private SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return sdf.format(new Date((long) value));
            }
        };
        xAxis.setValueFormatter(formatter);
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

    @Override
    public void onTaskCompleted(Object o) {
        List<?> listConsumption = null;
        if (o instanceof List<?>) {
            listConsumption = (List<?>) o;
        }

        float minValue = Float.MAX_VALUE, maxValue = Float.MIN_VALUE, minKey = Float.MAX_VALUE, maxKey = Float.MIN_VALUE, key, value, totalValue = 0;
        ArrayList<Entry> entries = new ArrayList<>();
        if (listConsumption != null && !listConsumption.isEmpty()) {
            for (Object obj : listConsumption) {
                Consumption consumption = (Consumption) obj;
                key = consumption.getRegistrationDate().getTime();
                value = consumption.getPower();

                minValue = Math.min(minValue, value);
                maxValue = Math.max(maxValue, value);
                minKey = Math.min(minKey, key);
                maxKey = Math.max(maxKey, key);
                totalValue += value;

                entries.add(new Entry(key, value));

            }
            setValueToChart(entries, minValue, maxValue, minKey, maxKey);
            mCost.setText(String.format(getResources().getString(R.string.cost), (totalValue) * kWhCost));
        } else {
            dialogError(getResources().getString(R.string.noConsumption));
        }
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTaskStarted() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onTaskFailed(String errorMessage) {
        dialogError(getResources().getString(R.string.consumptionError));
    }
}