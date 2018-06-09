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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.vonergy.R;
import com.vonergy.asyncTask.UserAsync;
import com.vonergy.connection.AppSession;
import com.vonergy.model.User;
import com.vonergy.util.Constants;

import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.vonergy.util.Util.refreshedToken;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;

    @BindView(R.id.edtLogin)
    TextView mLogin;

    @BindView(R.id.edtPassword)
    TextView mPassword;

    @BindView(R.id.progressBarLogin)
    ProgressBar mProgressBar;

    private GoogleSignInClient mGoogleSignInClient;
    private boolean checked = false;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(getResources().getString(R.string.vonergy));
        ButterKnife.bind(this);
        //mLogin.addTextChangedListener(MaskWatcher.buildCpf());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        sharedPreferences = getSharedPreferences(Constants.vonergyPreference, Context.MODE_PRIVATE);
        mLogin.setText(sharedPreferences.getString(Constants.loginPreference, ""));
        mPassword.setText(sharedPreferences.getString(Constants.passwordPreference, ""));

    }

    @OnClick(R.id.btnLogin)
    public void login() {
        User user = new User();
        user.setEmail(mLogin.getText().toString());
        user.setPassword(mPassword.getText().toString());
        user.setFirebaseToken(refreshedToken);

        try {
            UserAsync task = new UserAsync();
            task.setProgressBar(mProgressBar);
            AppSession.user = user;
            List<User> userList = task.execute(User.login).get();
            if (userList != null && !userList.isEmpty() && userList.get(0) != null) {
                AppSession.user = userList.get(0);
                if (checked) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Constants.loginPreference, AppSession.user.getEmail());
                    editor.putString(Constants.passwordPreference, AppSession.user.getPassword());
                    editor.apply();
                }
                Intent it = new Intent(this, VonergyActivity.class);
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

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            AppSession.user.setName(account.getDisplayName());
            AppSession.user.setEmail(account.getEmail());
            Intent it = new Intent(this, VonergyActivity.class);
            startActivity(it);
            finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            User u = new User();
            u.setEmail(account.getDisplayName());
            u.setPassword(account.getEmail());
            AppSession.user = u;
            Intent it = new Intent(this, VonergyActivity.class);
            startActivity(it);
            finish();
        } catch (ApiException e) {
            dialogError(getResources().getString(R.string.invalidLogin));
        }
    }

    @OnClick(R.id.sign_in_button)
    public void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
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
        Intent it = new Intent(this, ConfigActivity.class);
        startActivity(it);
    }
}