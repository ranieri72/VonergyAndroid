package com.vonergy.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Consumo {

    public static final int consumptionInRealTime = 0;
    public static final int consumptionPerHour = 1;
    public static final int dailyConsumption = 2;
    public static final int monthlyConsumption = 3;

    private long Id;

    @SerializedName("Corrente")
    private float current;

    @SerializedName("Tensao")
    private int voltage;

    @SerializedName("Potencia")
    private float power;

    @SerializedName("DataRegistro")
    private Date registrationDate;

    private List<Consumo> consumptionList;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public float getCurrent() {
        return current;
    }

    public void setCurrent(float current) {
        this.current = current;
    }

    public int getVoltage() {
        return voltage;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    public float getPower() {
        return power;
    }

    public void setPower(float power) {
        this.power = power;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<Consumo> getConsumptionList() {
        return consumptionList;
    }

    public void setConsumptionList(List<Consumo> consumptionList) {
        this.consumptionList = consumptionList;
    }
}
