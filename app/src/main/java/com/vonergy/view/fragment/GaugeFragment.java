package com.vonergy.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.anastr.speedviewlib.SpeedView;
import com.github.anastr.speedviewlib.util.OnPrintTickLabel;
import com.vonergy.R;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GaugeFragment extends Fragment {

    @BindView(R.id.temperature)
    TextView mTemperature;

    @BindView(R.id.tempView)
    SpeedView speedometer;

    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_gauge, container, false);
        unbinder = ButterKnife.bind(this, layout);
        mTemperature.setText(getResources().getString(R.string.buscando));
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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