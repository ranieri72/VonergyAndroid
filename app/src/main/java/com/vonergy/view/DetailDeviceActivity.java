package com.vonergy.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.vonergy.R;
import com.vonergy.asyncTask.EditDeviceAsync;
import com.vonergy.connection.iRequester;
import com.vonergy.model.Device;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailDeviceActivity extends AppCompatActivity implements iRequester {

    @BindView(R.id.name_device_value)
    EditText name;

    @BindView(R.id.model_value)
    EditText model;

    @BindView(R.id.brand_value)
    EditText brand;

    @BindView(R.id.minimumPower_value)
    EditText minimumPower;

    @BindView(R.id.maximumPower_value)
    EditText maximumPower;

    @BindView(R.id.minimumVoltage_value)
    EditText minimumVoltage;

    @BindView(R.id.maximumVoltage_value)
    EditText maximumVoltage;

    @BindView(R.id.switch_on_off_dt)
    Switch switchOnOff;

    @BindView(R.id.progressLoading)
    ProgressBar mProgressBar;

    Device device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_device);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        assert bundle != null;
        device = (Device) bundle.getSerializable("device");
        Log.i("ID - EQUIPAMENTO", String.valueOf(device.getId()));
        setDevice(device);
    }

    @OnClick(R.id.btnSalvarConfiguracao)
    public void edit() {
        EditDeviceAsync task = new EditDeviceAsync(this);
        device.setName(name.getText().toString());
        device.setModel(model.getText().toString());
        device.setBrand(brand.getText().toString());
        device.setMinimumPower(Float.parseFloat(minimumPower.getText().toString()));
        device.setMaximumPower(Float.parseFloat(maximumPower.getText().toString()));
        device.setMinimumVoltage(Float.parseFloat(minimumVoltage.getText().toString()));
        device.setMaximumVoltage(Float.parseFloat(maximumVoltage.getText().toString()));
        device.setStatus(switchOnOff.isChecked() ? 1 : 0);
        task.execute(device);
    }

    private void setDevice(Device device) {
        name.setText(device.getName());
        model.setText(device.getModel());
        brand.setText(device.getBrand());
        minimumPower.setText(String.valueOf(device.getMinimumPower()));
        maximumPower.setText(String.valueOf(device.getMaximumPower()));
        minimumVoltage.setText(String.valueOf(device.getMinimumVoltage()));
        maximumVoltage.setText(String.valueOf(device.getMaximumVoltage()));
        switchOnOff.setChecked(device.getStatus() == 1);
    }

    @Override
    public void onTaskCompleted(Object o) {
        device = (Device) o;
        if (device != null && !device.getName().isEmpty()) {
            setDevice(device);
            Toast.makeText(this, getString(R.string.savedMsg), Toast.LENGTH_LONG).show();
            EventBus.getDefault().post(device);
            finish();
        } else {
            Toast.makeText(this, getString(R.string.connectionError), Toast.LENGTH_LONG).show();
        }
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onTaskStarted() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTaskFailed(String errorMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(DetailDeviceActivity.this, getString(R.string.connectionError), Toast.LENGTH_LONG).show();
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }
}