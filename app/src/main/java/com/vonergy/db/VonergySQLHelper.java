package com.vonergy.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class VonergySQLHelper extends SQLiteOpenHelper{


    private static final String NOME_BANCO = "dbVonergy";

    private static final int VERSAO_BANCO = 1;


    public VonergySQLHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {

            db.execSQL(VonergyContract.SQL_CREATE_PARAMETROS);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + VonergyContract.TABLE_NAME_PARAMETROS);
        onCreate(db);
    }
}

