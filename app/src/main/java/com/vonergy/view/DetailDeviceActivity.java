package com.vonergy.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.vonergy.R;
import com.vonergy.view.adapter.DeviceAdapter;

public class DetailDeviceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_device);

        // Get the Intent that started this activity and extract the string
        Intent intent      = getIntent();
        String idDevice    = intent.getStringExtra("id_device");
        String modelDevice = intent.getStringExtra("model_device");
        String nameDevice  = intent.getStringExtra("name_device");

        ViewHolder vh;
        vh = new ViewHolder();

        //3)
        vh.name         = findViewById(R.id.name_device_value_dt);
        vh.model        = findViewById(R.id.model_value_dt);
        vh.id           = findViewById(R.id.deviceID_value_dt);
        vh.maxCost      = findViewById(R.id.maxCost_value_dt);
        vh.serialNumber = findViewById(R.id.serialNumber_value_dt);


        vh.name.setText(nameDevice);
        vh.model.setText(modelDevice);
        vh.id.setText(idDevice);
        vh.maxCost.setText("9854");
        vh.serialNumber.setText("SR-56874");

       // Toast.makeText(this.getApplicationContext(), "A INTENT FOI: "+message, Toast.LENGTH_SHORT).show();

    }

    private static class ViewHolder {
        TextView name;
        TextView model;
        TextView id;
        TextView maxCost;
        TextView serialNumber;
    }

}
