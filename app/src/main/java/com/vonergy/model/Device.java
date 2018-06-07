package com.vonergy.model;

import com.google.gson.annotations.SerializedName;

public class Device {

    @SerializedName("Id")
    private long id;

    @SerializedName("NomeEquipamento")
    private String name;

    @SerializedName("Modelo")
    private String model;

    @SerializedName("Marca")
    private String brand;

    @SerializedName("PotenciaMinima")
    private float minimumPower;

    @SerializedName("PotenciaMaxima")
    private float maximumPower;

    @SerializedName("TensaoMinima")
    private float minimumVoltage;

    @SerializedName("TensaoMaxima")
    private float maximumVoltage;

    @SerializedName("CorrentMinima")
    private float minimalCurrent;

    @SerializedName("CorrenteMaxima")
    private float maximumCurrent;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public float getMinimumPower() {
        return minimumPower;
    }

    public void setMinimumPower(float minimumPower) {
        this.minimumPower = minimumPower;
    }

    public float getMaximumPower() {
        return maximumPower;
    }

    public void setMaximumPower(float maximumPower) {
        this.maximumPower = maximumPower;
    }

    public float getMinimumVoltage() {
        return minimumVoltage;
    }

    public void setMinimumVoltage(float minimumVoltage) {
        this.minimumVoltage = minimumVoltage;
    }

    public float getMaximumVoltage() {
        return maximumVoltage;
    }

    public void setMaximumVoltage(float maximumVoltage) {
        this.maximumVoltage = maximumVoltage;
    }

    public float getMinimalCurrent() {
        return minimalCurrent;
    }

    public void setMinimalCurrent(float minimalCurrent) {
        this.minimalCurrent = minimalCurrent;
    }

    public float getMaximumCurrent() {
        return maximumCurrent;
    }

    public void setMaximumCurrent(float maximumCurrent) {
        this.maximumCurrent = maximumCurrent;
    }
}
