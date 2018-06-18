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

import com.github.anastr.speedviewlib.SpeedView;
import com.github.anastr.speedviewlib.util.OnPrintTickLabel;
import com.vonergy.R;
import com.vonergy.asyncTask.ConsumptionAsync;
import com.vonergy.connection.iRequester;
import com.vonergy.db.DAOVonergy;
import com.vonergy.model.Consumption;
import com.vonergy.model.Parametro;

import java.util.List;
import java.util.Locale;

public class ConsumoTempoRealFragment extends Fragment implements iRequester {

    private static final String CHAVE_TIPO_CONSUMO = "CHAVE_TIPO_CONSUMO";

    private int tipoConsumo;

    private String formatoData;

    private Handler mHandler;

    private ProgressBar mProgressBar;

    SpeedView speedometer;

    DAOVonergy mDAO;

    public ConsumoTempoRealFragment() {
        // Required empty public constructor
    }

    public static ConsumoTempoRealFragment newInstance(int tipoConsumo) {
        ConsumoTempoRealFragment fragment = new ConsumoTempoRealFragment();
        Bundle args = new Bundle();
        args.putInt(CHAVE_TIPO_CONSUMO, tipoConsumo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_consumo_tempo_real, container, false);
        speedometer = layout.findViewById(R.id.tempView);
        mProgressBar = layout.findViewById(R.id.progressLoading);
        mDAO = new DAOVonergy(getActivity());

        Bundle args = getArguments();
        tipoConsumo = args.getInt(CHAVE_TIPO_CONSUMO, 0);

        mHandler = new Handler();

        return layout;
    }

    private void updatePower() {
        ConsumptionAsync task = new ConsumptionAsync(this);
        task.execute(tipoConsumo);
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            updatePower();
            int mInterval = 10000;
            mHandler.postDelayed(mStatusChecker, mInterval);
        }
    };


    public void setupGauger(float tempValue, int minTemp, int maxTemp) {
        int hundred = Math.round(maxTemp);
        int seventyFive = (int) Math.round(maxTemp * 0.75);
        int fifty = (int) Math.round(maxTemp * 0.5);
        int twentyFive = (int) Math.round(maxTemp * 0.25);

        speedometer.setMinSpeed(minTemp);
        speedometer.setUnit("kWh");

        Parametro parametro = mDAO.getParametros();

        if (parametro != null) {

            float minimo = parametro.getLimiteMinimo();
            float medio = parametro.getLimiteMedio();
            float maximo = parametro.getLimiteMaximo();
            maximo = Math.max(maximo, maxTemp);

            float porcentagemMinimaTemp = (minimo / maximo) * 100;
            float porcentagemMaximaTemp = (medio / maximo) * 100;

            int porcentagemMinima = (int) porcentagemMinimaTemp;
            int porcentagemMaxima = (int) porcentagemMaximaTemp;

            speedometer.setMaxSpeed((int) maximo);
            speedometer.setLowSpeedPercent(porcentagemMinima);
            speedometer.setMediumSpeedPercent(porcentagemMaxima);

            if (minimo > 0) {
                speedometer.setTicks(0, (int) minimo, (int) medio, (int) maximo);
            } else {
                speedometer.setTicks((int) minimo, (int) medio, (int) maximo);
            }
        } else {

            speedometer.setMaxSpeed(maxTemp);
            speedometer.setTicks(0, twentyFive, fifty, seventyFive, hundred);
        }
        speedometer.speedTo(tempValue);
        speedometer.setOnPrintTickLabel(new OnPrintTickLabel() {
            @Override
            public String getTickLabel(int tickPosition, int tick) {
                return String.format(Locale.getDefault(), "%d kWh", tick);
            }
        });
    }

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
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
    public void onStart() {
        super.onStart();
        startRepeatingTask();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopRepeatingTask();
//        unbinder.unbind();
    }

    @Override
    public void onTaskCompleted(Object o) {
        float maxValue = Float.MIN_VALUE, value;
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
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onTaskStarted() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTaskFailed(String errorMessage) {
        dialogError(getResources().getString(R.string.consumptionError));
    }
}