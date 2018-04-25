package com.vonergy.view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vonergy.R;
import com.vonergy.util.Constants;
import com.vonergy.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ConfigFragment extends Fragment {

    @BindView(R.id.edtServerIP)
    TextView mServerIP;

    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.activity_config, container, false);
        unbinder = ButterKnife.bind(this, layout);
        mServerIP.setText(Util.ipv4);
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnSave)
    public void save() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.vonergyPreference, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(Constants.serverIpPreference, mServerIP.getText().toString());
        editor.apply();
        Util.ipv4 = mServerIP.getText().toString();
        Toast.makeText(getActivity(), getResources().getString(R.string.savedMsg), Toast.LENGTH_LONG).show();
    }
}
