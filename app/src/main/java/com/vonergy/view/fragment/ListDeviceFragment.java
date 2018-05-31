package com.vonergy.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ListDeviceFragment extends Fragment {

    @BindView(R.id.listDevice)
    ListView mListView;

    Unbinder unbinder;
    DeviceAdapter mAdapter;
    List<Device> mListDevice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_list_device, container, false);
        unbinder = ButterKnife.bind(this, layout);

        DeviceAsync task = new DeviceAsync();
        try {
            mListDevice = task.execute().get();
            if (mListDevice != null && mListDevice.size() > 0) {
                mAdapter = new DeviceAdapter(getActivity(), mListDevice);
                mListView.setAdapter(mAdapter);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
