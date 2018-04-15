package com.vonergy.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Consumo {

    private long Id;

    @SerializedName("Corrente")
    private int current;

    @SerializedName("Tensao")
    private int voltage;

    @SerializedName("Potencia")
    private int power;

    @SerializedName("DataRegistro")
    private Date registrationDate;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getVoltage() {
        return voltage;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
}
