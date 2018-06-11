package com.vonergy.model;

public class Parametro {

    private float limiteMinimo, limiteMedio, limiteMaximo;

    public Parametro(float limiteMinimo, float limiteMedio, float limiteMaximo) {
        this.limiteMinimo = limiteMinimo;
        this.limiteMedio = limiteMedio;
        this.limiteMaximo = limiteMaximo;
    }

    public Parametro(){

    }

    public float getLimiteMinimo() {
        return limiteMinimo;
    }

    public void setLimiteMinimo(float limiteMinimo) {
        this.limiteMinimo = limiteMinimo;
    }

    public float getLimiteMedio() {
        return limiteMedio;
    }

    public void setLimiteMedio(float limiteMedio) {
        this.limiteMedio = limiteMedio;
    }

    public float getLimiteMaximo() {
        return limiteMaximo;
    }

    public void setLimiteMaximo(float limiteMaximo) {
        this.limiteMaximo = limiteMaximo;
    }
}
