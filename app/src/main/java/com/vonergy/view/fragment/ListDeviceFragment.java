package com.vonergy.view.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import butterknife.Unbinder;

public class ListDeviceFragment extends Fragment implements iRequester {

    @BindView(R.id.listDevice)
    ListView mListView;

    @BindView(R.id.indeterminateBar)
    ProgressBar mProgressBar;

    Unbinder unbinder;
    DeviceAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_list_device, container, false);
        unbinder = ButterKnife.bind(this, layout);

        DeviceAsync task = new DeviceAsync(this);
        task.execute();
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onTaskCompleted(Object o) {
        List<?> mListDevice = null;
        if (o instanceof List<?>) {
            mListDevice = (List<?>) o;
        }

        if (mListDevice != null && mListDevice.size() > 0) {
            mAdapter = new DeviceAdapter(getActivity(), (List<Device>) mListDevice);
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
        dialogError(getResources().getString(R.string.consumptionError));
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
}
