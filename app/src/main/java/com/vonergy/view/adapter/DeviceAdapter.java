package com.vonergy.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vonergy.R;
import com.vonergy.model.Device;

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
        Device device = getItem(position);

        //2)
        DeviceAdapter.ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_device, null);
            vh = new DeviceAdapter.ViewHolder();

            //3)
            vh.name = convertView.findViewById(R.id.name_device);
            vh.model = convertView.findViewById(R.id.model_value);
            vh.brand = convertView.findViewById(R.id.brand_value);
            vh.minimumPower = convertView.findViewById(R.id.minimumPower_value);
            vh.maximumPower = convertView.findViewById(R.id.maximumPower_value);
            vh.minimumVoltage = convertView.findViewById(R.id.minimumVoltage_value);
            vh.maximumVoltage = convertView.findViewById(R.id.maximumVoltage_value);
            vh.minimalCurrent = convertView.findViewById(R.id.minimalCurrent_value);
            vh.maximumCurrent = convertView.findViewById(R.id.maximumCurrent_value);

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
        return convertView;
    }

    private static class ViewHolder {
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
