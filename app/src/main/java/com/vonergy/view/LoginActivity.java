package com.vonergy.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vonergy.R;
import com.vonergy.asyncTask.UserAsync;
import com.vonergy.connection.AppSession;
import com.vonergy.model.User;
import com.vonergy.util.Constants;
import com.vonergy.view.fragment.ConfigFragment;

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

    private boolean checked = false;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(getResources().getString(R.string.vonergy));
        ButterKnife.bind(this);
        //mLogin.addTextChangedListener(MaskWatcher.buildCpf());

        sharedPreferences = getSharedPreferences(Constants.vonergyPreference, Context.MODE_PRIVATE);
        mLogin.setText(sharedPreferences.getString(Constants.loginPreference, ""));
        mPassword.setText(sharedPreferences.getString(Constants.passwordPreference, ""));

        // temp
        mLogin.setText("irineutesteemail@gmail.com");
        mPassword.setText("123");
    }

    @OnClick(R.id.btnLogin)
    public void login() {
        User u = new User();
        u.setEmail(mLogin.getText().toString());
        u.setPassword(mPassword.getText().toString());
        AppSession.user = u;
        try {
            UserAsync task = new UserAsync();
            task.setProgressBar(mProgressBar);
            AppSession.user = task.execute(User.login).get().get(0);
            if (AppSession.user != null) {
                if (checked) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Constants.loginPreference, u.getEmail());
                    editor.putString(Constants.passwordPreference, u.getPassword());
                    editor.apply();
                }
                Intent it = new Intent(this, MainActivity.class);
                startActivity(it);
                finish();
            } else {
                dialogError(getResources().getString(R.string.invalidLogin));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            dialogError(getResources().getString(R.string.connectionError));
        } catch (ExecutionException e) {
            e.printStackTrace();
            dialogError(getResources().getString(R.string.connectionError));
        }
    }

    public void onCheckboxClicked(View view) {
        checked = ((CheckBox) view).isChecked();
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

    private void dialogError(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.error));
        builder.setMessage(msg);

        builder.setNeutralButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        AlertDialog alerta = builder.create();
        alerta.show();
    }

    private void optionConfig() {
        Intent it = new Intent(this, ConfigFragment.class);
        startActivity(it);
    }
}
