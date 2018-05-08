package com.vonergy.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Consumption {

    public static final int consumptionInRealTime = 0;
    public static final int consumptionPerHour = 1;
    public static final int dailyConsumption = 2;
    public static final int weeklyConsumption = 3;
    public static final int monthlyConsumption = 4;
    public static final int annualConsumption = 5;

    private long Id;

    @SerializedName("Potencia")
    private float power;

    @SerializedName("DataRegistro")
    private Date registrationDate;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
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
}
