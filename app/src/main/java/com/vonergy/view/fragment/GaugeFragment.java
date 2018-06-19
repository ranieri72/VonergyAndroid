package com.vonergy.view.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.anastr.speedviewlib.SpeedView;
import com.github.anastr.speedviewlib.util.OnPrintTickLabel;
import com.vonergy.R;
import com.vonergy.asyncTask.ConsumptionAsync;
import com.vonergy.connection.iRequester;
import com.vonergy.model.Consumption;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.vonergy.model.Consumption.kWhCost;

public class GaugeFragment extends Fragment implements iRequester {

    @BindView(R.id.temperature)
    TextView mTemperature;

    @BindView(R.id.cost)
    TextView mCost;

    @BindView(R.id.tempView)
    SpeedView speedometer;

    Unbinder unbinder;
    private Handler mHandler;
    private int historyType;
    private ProgressBar mProgressBar;
    private float maxValue;

    public static GaugeFragment newInstance(int historyType) {
        GaugeFragment f = new GaugeFragment();
        Bundle args = new Bundle();
        args.putInt("historyType", historyType);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_gauge, container, false);
        unbinder = ButterKnife.bind(this, layout);
        Bundle args = getArguments();
        historyType = args.getInt("historyType", 0);
        mProgressBar = getActivity().findViewById(R.id.toolbar_progress_bar);
        mTemperature.setText(getResources().getString(R.string.buscando));
        mHandler = new Handler();
        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        startRepeatingTask();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopRepeatingTask();
        unbinder.unbind();
    }

    private void updatePower() {
        ConsumptionAsync task = new ConsumptionAsync(this);
        task.execute(historyType);
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            updatePower();
            int mInterval = 10000;
            mHandler.postDelayed(mStatusChecker, mInterval);
        }
    };

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    public void setupGauger(float tempValue, int minTemp, int maxTemp) {
        int hundred = Math.round(maxValue * 1);
        int seventyFive = (int) Math.round(maxValue * 0.75);
        int fifty = (int) Math.round(maxValue * 0.5);
        int twentyFive = (int) Math.round(maxValue * 0.25);

        mTemperature.setText(String.format(getResources().getString(R.string.kilowatt), tempValue));
        mCost.setText(String.format(getResources().getString(R.string.cost), tempValue * kWhCost));
        speedometer.setMinSpeed(minTemp);
        speedometer.setMaxSpeed(maxTemp);
        speedometer.speedTo(tempValue);
        speedometer.setLowSpeedPercent(50);
        speedometer.setMediumSpeedPercent(75);
        speedometer.setTicks(0, twentyFive, fifty, seventyFive, hundred);
        speedometer.setUnit("kWh");
        speedometer.setOnPrintTickLabel(new OnPrintTickLabel() {
            @Override
            public String getTickLabel(int tickPosition, int tick) {
                return String.format(Locale.getDefault(), "%d kWh", tick);
            }
        });
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
        //float maxValue = Float.MIN_VALUE, value;
        float value;
        maxValue = 50;
        List<?> listConsumption = null;
        if (o instanceof List<?>) {
            listConsumption = (List<?>) o;
        }

        if (listConsumption != null && !listConsumption.isEmpty()) {
            Consumption consumption = (Consumption) listConsumption.get(0);
            value = consumption.getPower();
            maxValue = Math.max(maxValue, value);
            setupGauger(value, 0, Math.round(maxValue));
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
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialogError(getResources().getString(R.string.consumptionError));
                }
            });
        }
    }
}