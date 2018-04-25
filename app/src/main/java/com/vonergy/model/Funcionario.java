package com.vonergy.model;

import com.google.gson.annotations.SerializedName;

public class Funcionario {

    @SerializedName("Nome")
    private String name;

    @SerializedName("Email")
    private String email;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
