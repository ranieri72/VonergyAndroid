package com.vonergy.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vonergy.model.Parametro;
import com.vonergy.util.Constants;

public class DAOVonergy {

    private VonergySQLHelper mHelper;

    Context mContext;

    public DAOVonergy(Context context) {
        this.mHelper = new VonergySQLHelper(context);
        mContext = context;
    }

    public void insertParamentos(Parametro parametro) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = parseParametros(parametro);

        db.insertOrThrow(VonergyContract.TABLE_NAME_PARAMETROS, null, values);

        db.close();
    }

    public ContentValues parseParametros(Parametro parametro){
        ContentValues values = new ContentValues();
        values.put(Constants.LIMITE_MINIMO, parametro.getLimiteMinimo());
        values.put(Constants.LIMITE_MEDIO, parametro.getLimiteMedio());
        values.put(Constants.LIMITE_MAXIMO, parametro.getLimiteMaximo());
        return values;
    }

    public void updateParametros(Parametro parametro) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        ContentValues values = parseParametros(parametro);
        db.update(VonergyContract.TABLE_NAME_PARAMETROS,
                values,
                "", null);
        db.close();
    }

    public boolean existeParametro() {
        boolean existe = false;
        SQLiteDatabase db = mHelper.getReadableDatabase();

        String sql = "SELECT * FROM " + VonergyContract.TABLE_NAME_PARAMETROS;


        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            existe = true;
        }
        cursor.close();
        db.close();
        return existe;
    }

    public Parametro getParametros(){
        Parametro parametro = null;
        SQLiteDatabase db = mHelper.getReadableDatabase();

        String sql = "SELECT * FROM " + VonergyContract.TABLE_NAME_PARAMETROS;


        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            int idxMinimo = cursor.getColumnIndex(Constants.LIMITE_MINIMO);
            int idxMedio = cursor.getColumnIndex(Constants.LIMITE_MEDIO);
            int idxMaximo = cursor.getColumnIndex(Constants.LIMITE_MAXIMO);
            parametro = new Parametro();
            while (cursor.moveToNext()) {
                float minimo = cursor.getFloat(idxMinimo);
                float medio = cursor.getFloat(idxMedio);
                float maximo = cursor.getFloat(idxMaximo);
                parametro.setLimiteMinimo(minimo);
                parametro.setLimiteMedio(medio);
                parametro.setLimiteMaximo(maximo);

            }
        }
        cursor.close();
        db.close();
        return parametro;
    }



}
