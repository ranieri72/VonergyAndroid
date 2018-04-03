package com.vonergy.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.vonergy.R;
import com.vonergy.util.Constants;
import com.vonergy.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfigActivity extends AppCompatActivity {

    @BindView(R.id.edtServerIP)
    TextView mServerIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        setTitle(getResources().getString(R.string.settings));
        ButterKnife.bind(this);

        mServerIP.setText(Util.ipv4);
    }

    @OnClick(R.id.btnSave)
    public void save() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.serverIpPreference, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(Constants.serverIpPreference, mServerIP.getText().toString());
        editor.apply();
        Util.ipv4 = mServerIP.getText().toString();
        Toast.makeText(this, getResources().getString(R.string.savedMsg), Toast.LENGTH_LONG).show();
    }
}
