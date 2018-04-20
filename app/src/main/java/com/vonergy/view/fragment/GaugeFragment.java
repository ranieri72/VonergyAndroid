package com.vonergy.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.anastr.speedviewlib.SpeedView;
import com.github.anastr.speedviewlib.util.OnPrintTickLabel;
import com.vonergy.R;
import com.vonergy.asyncTask.ConsumptionAsync;
import com.vonergy.model.Consumo;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GaugeFragment extends Fragment {

    @BindView(R.id.temperature)
    TextView mTemperature;

    @BindView(R.id.tempView)
    SpeedView speedometer;

    Unbinder unbinder;
    private Handler mHandler;
    private int historyType;

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
        //float maxValue = Float.MIN_VALUE, value;
        float maxValue = 400000, value;

        try {
            ConsumptionAsync task = new ConsumptionAsync(getActivity());
            List<Consumo> listConsumption = task.execute(historyType).get();
            if (listConsumption != null && !listConsumption.isEmpty()) {
                value = listConsumption.get(0).getPower();
                maxValue = Math.max(maxValue, value);
                setupGauger(value, 0, Math.round(maxValue));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), getResources().getString(R.string.connectionError) + " " + e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), getResources().getString(R.string.connectionError) + " " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
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
        mTemperature.setText(String.format(getResources().getString(R.string.kilowatt), tempValue));
        speedometer.setMinSpeed(minTemp);
        speedometer.setMaxSpeed(maxTemp);
        speedometer.speedTo(tempValue);
        speedometer.setLowSpeedPercent(50);
        speedometer.setMediumSpeedPercent(75);
        //speedometer.setTicks(15, 20, 25, 30, 35);
        speedometer.setOnPrintTickLabel(new OnPrintTickLabel() {
            @Override
            public String getTickLabel(int tickPosition, int tick) {
                return String.format(Locale.getDefault(), "%d kW", tick);
            }
        });
    }
}