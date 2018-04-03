package com.vonergy.model;

import com.google.gson.annotations.SerializedName;

public class Funcionario {

    @SerializedName("Cpf")
    private String cpf;

    @SerializedName("Senha")
    private String password;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
