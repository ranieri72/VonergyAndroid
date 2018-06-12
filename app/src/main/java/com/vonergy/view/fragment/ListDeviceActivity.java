package com.vonergy.view.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.vonergy.R;
import com.vonergy.asyncTask.DeviceAsync;
import com.vonergy.model.Device;
import com.vonergy.view.adapter.DeviceAdapter;

import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ListDeviceActivity extends AppCompatActivity {

    @BindView(R.id.listDevice)
    ListView mListView;

    Unbinder unbinder;
    DeviceAdapter mAdapter;
    List<Device> mListDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_device);
        ButterKnife.bind(this);

        DeviceAsync task = new DeviceAsync();
        try {
            mListDevice = task.execute().get();
            if (mListDevice != null && mListDevice.size() > 0) {
                mAdapter = new DeviceAdapter(this, mListDevice);
                mListView.setAdapter(mAdapter);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}







