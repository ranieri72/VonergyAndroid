package com.vonergy.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Toast;

import com.vonergy.R;
import com.vonergy.connection.AppSession;
import com.vonergy.model.User;
import com.vonergy.util.Mask;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        setDadosUsuario();
    }

    void setDadosUsuario() {
        mUsuarioLogado = AppSession.user;

        mEdtNome.setText(mUsuarioLogado.getName());
        mEdtEmail.setText(mUsuarioLogado.getEmail());
        mEdtCelular.setText(mUsuarioLogado.getCellphone());
        //Mask.insert("(##) #####-####", mEdtCelular); Celular já vem com mascara do banco de dados
    }
}
