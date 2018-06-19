package com.vonergy.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.vonergy.R;
import com.vonergy.asyncTask.DeviceAsync;
import com.vonergy.connection.iRequester;
import com.vonergy.model.Device;
import com.vonergy.view.adapter.DeviceAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListDeviceActivity extends AppCompatActivity implements iRequester {

    @BindView(R.id.listDevice)
    ListView mListView;

    @BindView(R.id.progressLoading)
    ProgressBar mProgressBar;

    DeviceAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_device);
        ButterKnife.bind(this);

        DeviceAsync task = new DeviceAsync(this);
        task.execute();
    }

    @Override
    public void onTaskCompleted(Object o) {
        List<?> mListDevice = null;
        if (o instanceof List<?>) {
            mListDevice = (List<?>) o;
        }

        if (mListDevice != null && mListDevice.size() > 0) {
            mAdapter = new DeviceAdapter(this, (List<Device>) mListDevice);
            mListView.setAdapter(mAdapter);
        }
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTaskStarted() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onTaskFailed(String errorMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialogError(getResources().getString(R.string.consumptionError));
            }
        });
    }

    private void dialogError(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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







