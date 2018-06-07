package com.vonergy.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.vonergy.R;
import com.vonergy.asyncTask.UserAsync;
import com.vonergy.connection.ConnectionConstants;
import com.vonergy.model.User;
import com.vonergy.util.Constants;
import com.vonergy.util.Mask;

import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.vonergy.util.Util.refreshedToken;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.edtNome)
    EditText mEdtNome;

    @BindView(R.id.edtEmail)
    EditText mEdtEmail;

    @BindView(R.id.edtCelular)
    EditText mEdtCelular;

    @BindView(R.id.toolbar_profile)
    Toolbar mToolbar;


    User mUsuarioLogado;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.vonergyPreference, Context.MODE_PRIVATE);
        mUsuarioLogado = new User();
        mUsuarioLogado.setEmail(sharedPreferences.getString(Constants.loginPreference, ConnectionConstants.ipv4));
        mUsuarioLogado.setPassword(sharedPreferences.getString(Constants.passwordPreference, ConnectionConstants.ipv4));
        mUsuarioLogado.setFirebaseToken(refreshedToken);
        setDadosUsuario();
    }

    void setDadosUsuario(){
        try {
            UserAsync task = new UserAsync();
            List<User> userList = task.execute(User.login).get();
            if(!userList.isEmpty()){
                mUsuarioLogado = userList.get(0);

                mEdtNome.setText(mUsuarioLogado.getName());
                mEdtEmail.setText(mUsuarioLogado.getEmail());
                mEdtCelular.setText(mUsuarioLogado.getCellphone());
                Mask.insert("(##) #####-####", mEdtCelular);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
