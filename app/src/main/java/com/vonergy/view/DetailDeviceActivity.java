package com.vonergy.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.vonergy.R;
import com.vonergy.asyncTask.EditDeviceAsync;
import com.vonergy.model.Device;

import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailDeviceActivity extends AppCompatActivity {

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
        setDevice(device);
    }

    @OnClick(R.id.btnSave)
    public void edit() {
        EditDeviceAsync task = new EditDeviceAsync();
        try {
            device.setName(name.getText().toString());
            device.setModel(model.getText().toString());
            device.setBrand(brand.getText().toString());
            device.setMinimumPower(Float.parseFloat(minimumPower.getText().toString()));
            device.setMaximumPower(Float.parseFloat(maximumPower.getText().toString()));
            device.setMinimumVoltage(Float.parseFloat(minimumVoltage.getText().toString()));
            device.setMaximumVoltage(Float.parseFloat(maximumVoltage.getText().toString()));
            device.setStatus(switchOnOff.isChecked() ? 1 : 0);

            device = task.execute(device).get();
            if (device != null && !device.getName().isEmpty()) {
                setDevice(device);
                Toast.makeText(this, getString(R.string.savedMsg), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, getString(R.string.connectionError), Toast.LENGTH_LONG).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.connectionError), Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.connectionError), Toast.LENGTH_LONG).show();
        }
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
}
