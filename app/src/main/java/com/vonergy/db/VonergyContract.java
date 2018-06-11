package com.vonergy.db;

import com.vonergy.util.Constants;

public interface  VonergyContract {


    String TABLE_NAME_PARAMETROS = "PARAMETROS";


    String SQL_CREATE_PARAMETROS = "CREATE TABLE " + TABLE_NAME_PARAMETROS + " ("
            + Constants.LIMITE_MINIMO + " TEXT,"
            + Constants.LIMITE_MEDIO + " TEXT,"
            + Constants.LIMITE_MAXIMO + " TEXT" + ")" ;
}
