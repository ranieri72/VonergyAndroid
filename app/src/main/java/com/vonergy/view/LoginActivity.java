package com.vonergy.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vonergy.R;
import com.vonergy.asyncTask.LoginAsync;
import com.vonergy.connection.AppSession;
import com.vonergy.model.Funcionario;
import com.vonergy.util.Constants;
import com.vonergy.util.Util;

import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edtLogin)
    TextView mLogin;

    @BindView(R.id.edtPassword)
    TextView mPassword;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(getResources().getString(R.string.login));
        ButterKnife.bind(this);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.serverIpPreference, Context.MODE_PRIVATE);
        Util.ipv4 = sharedPreferences.getString(Constants.serverIpPreference, com.vonergy.connection.Constants.ipv4);
    }

    @OnClick(R.id.btnLogin)
    public void login() {
        Funcionario u = new Funcionario();
        u.setCpf(mLogin.getText().toString());
        u.setPassword(mPassword.getText().toString());
        AppSession.user = u;

        mProgressBar.setVisibility(View.VISIBLE);
        try {
            new LoginAsync().execute().get();
            if (AppSession.user == null) {
                Toast.makeText(this, getResources().getString(R.string.invalidLogin), Toast.LENGTH_LONG).show();
            } else {
                Intent it = new Intent(this, MainActivity.class);
                startActivity(it);
                finish();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(this, getResources().getString(R.string.connectionError), Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            e.printStackTrace();
            Toast.makeText(this, getResources().getString(R.string.connectionError), Toast.LENGTH_LONG).show();
        }
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_config:
                optionConfig();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void optionConfig() {
        Intent it = new Intent(this, ConfigActivity.class);
        startActivity(it);
    }
}
