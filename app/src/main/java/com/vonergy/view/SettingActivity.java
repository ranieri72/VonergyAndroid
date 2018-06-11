package com.vonergy.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vonergy.R;
import com.vonergy.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_setting)
    Toolbar mToolbar;

    @BindView(R.id.edtLimiteMinimo)
    EditText mEdtLimiteMinimo;

    @BindView(R.id.edtLimiteMedio)
    EditText mEdtLimiteMedio;

    @BindView(R.id.edtLimiteMaximo)
    EditText mEdtLimiteMaximo;

    @BindView(R.id.btnConfigurar)
    Button mBtnConfigurar;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        preferences = getSharedPreferences(Constants.vonergyPreference, Context.MODE_PRIVATE);

        if(preferences.getFloat(Constants.LIMITE_MINIMO, 0) != 0){
            mEdtLimiteMinimo.setText(String.valueOf(preferences.getFloat(Constants.LIMITE_MINIMO, 0)));
        }

        if(preferences.getFloat(Constants.LIMITE_MEDIO, 0) != 0){
            mEdtLimiteMedio.setText(String.valueOf(preferences.getFloat(Constants.LIMITE_MEDIO, 0)));
        }

        if(preferences.getFloat(Constants.LIMITE_MAXIMO, 0) != 0){
            mEdtLimiteMaximo.setText(String.valueOf(preferences.getFloat(Constants.LIMITE_MAXIMO, 0)));
        }

        mBtnConfigurar.setOnClickListener(new BotaoConfigurar());
    }

    class BotaoConfigurar implements View.OnClickListener{

        @Override
        public void onClick(View view) {

            float limiteMinimo, limiteMedio, limiteMaximo;

            limiteMinimo = Float.parseFloat(mEdtLimiteMinimo.getText().toString());
            limiteMedio = Float.parseFloat(mEdtLimiteMedio.getText().toString());
            limiteMaximo = Float.parseFloat(mEdtLimiteMaximo.getText().toString());

            if(limiteMinimo == 0 || limiteMedio == 0 || limiteMaximo == 0){
                //ERROR
            }

            if(limiteMedio < limiteMinimo){
                Toast.makeText(SettingActivity.this, "O limite médio não pode ser menor que o mínimo!", Toast.LENGTH_SHORT).show();
                mEdtLimiteMinimo.setError("Verificar");
                mEdtLimiteMedio.setError("Verificar");
            }

            if(limiteMaximo < limiteMinimo){
                Toast.makeText(SettingActivity.this, "O limite máximo não pode ser menor que o mínimo!", Toast.LENGTH_SHORT).show();
                mEdtLimiteMaximo.setError("Verificar");
                mEdtLimiteMinimo.setError("Verificar");
            }

            if(limiteMaximo < limiteMedio){
                Toast.makeText(SettingActivity.this, "O limite máximo não pode ser menor que o médio!", Toast.LENGTH_SHORT).show();
                mEdtLimiteMaximo.setError("Verificar");
                mEdtLimiteMedio.setError("Verificar");
            }

            SharedPreferences.Editor editor = preferences.edit();

            editor.putFloat(Constants.LIMITE_MINIMO, limiteMinimo);
            editor.putFloat(Constants.LIMITE_MEDIO, limiteMedio);
            editor.putFloat(Constants.LIMITE_MAXIMO, limiteMaximo);

            Toast.makeText(SettingActivity.this, "Configurações realizadas com sucesso", Toast.LENGTH_SHORT).show();

            Intent it = new Intent(SettingActivity.this, VonergyActivity.class);
            startActivity(it);
            finish();

        }
    }
}

