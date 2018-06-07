package com.vonergy.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vonergy.R;
import com.vonergy.model.Device;
import com.vonergy.view.DetailDeviceActivity;

import java.util.List;

public class DeviceAdapter extends ArrayAdapter<Device> {

    private Context context;

    public DeviceAdapter(@NonNull Context context, @NonNull List<Device> objects) {
        super(context, 0, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //1)
        final Device device = getItem(position);

        //2)
        DeviceAdapter.ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_device, null);

            vh = new DeviceAdapter.ViewHolder();
            TextView txt = convertView.findViewById(R.id.name_device);

            //3)
           // vh.listDevices = convertView.findViewById(R.id.list_devices);
            vh.name = convertView.findViewById(R.id.name_device);
            vh.model = convertView.findViewById(R.id.model_value);
            vh.brand = convertView.findViewById(R.id.brand_value);
            vh.minimumPower = convertView.findViewById(R.id.minimumPower_value);
            vh.maximumPower = convertView.findViewById(R.id.maximumPower_value);
            vh.minimumVoltage = convertView.findViewById(R.id.minimumVoltage_value);
            vh.maximumVoltage = convertView.findViewById(R.id.maximumVoltage_value);
            vh.minimalCurrent = convertView.findViewById(R.id.minimalCurrent_value);
            vh.maximumCurrent = convertView.findViewById(R.id.maximumCurrent_value);

            vh.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "O id foi: "+device.getId()+" E o nome é: "+device.getName().toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), DetailDeviceActivity.class);
                    //Get the value of the item you clicked
                    intent.putExtra("id_device", device.getId());
                    intent.putExtra("model_device", device.getModel().toString());
                    intent.putExtra("name_device", device.getName().toString());
                    context.startActivity(intent);

                }
            });

            convertView.setTag(vh);
        } else {
            vh = (DeviceAdapter.ViewHolder) convertView.getTag();
        }

        assert device != null;
        vh.name.setText(device.getName());
        vh.model.setText(device.getModel());
        vh.brand.setText(device.getBrand());
        vh.minimumPower.setText(String.valueOf(device.getMinimumPower()));
        vh.maximumPower.setText(String.valueOf(device.getMaximumPower()));
        vh.minimumVoltage.setText(String.valueOf(device.getMinimumVoltage()));
        vh.maximumVoltage.setText(String.valueOf(device.getMaximumVoltage()));
        vh.minimalCurrent.setText(String.valueOf(device.getMinimalCurrent()));
        vh.maximumCurrent.setText(String.valueOf(device.getMaximumCurrent()));


        //4)
        // action to turn ON and OFF the devices
/*        vh.listDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });*/

        return convertView;
    }

    public static class ViewHolder {
        TextView name;
        TextView model;
        TextView brand;
        TextView minimumPower;
        TextView maximumPower;
        TextView minimumVoltage;
        TextView maximumVoltage;
        TextView minimalCurrent;
        TextView maximumCurrent;
    }
}
