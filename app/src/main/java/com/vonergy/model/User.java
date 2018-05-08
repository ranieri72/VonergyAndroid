package com.vonergy.model;

import com.google.gson.annotations.SerializedName;

public class User {

    public static final int male = 1;
    public static final int female = 2;

    public static final int single = 1;
    public static final int married = 2;
    public static final int divorced = 3;
    public static final int widowed = 4;

    public static final int login = 1;
    public static final int listUser = 2;

    @SerializedName("Nome")
    private String name;

    @SerializedName("Email")
    private String email;

    @SerializedName("Cpf")
    private String cpf;

    @SerializedName("Senha")
    private String password;

    @SerializedName("Rg")
    private String rg;

    @SerializedName("OrgaoExpeditor")
    private String rgsAgency;

    @SerializedName("DataNascimento")
    private String birthDate;

    @SerializedName("NomeMae")
    private String mothersName;

    @SerializedName("NomePai")
    private String fathersName;

    @SerializedName("Sexo")
    private int sex;

    @SerializedName("EstadoCivil")
    private int maritalStatus;

    @SerializedName("Logradouro")
    private String street;

    @SerializedName("Bairro")
    private String neighborhood;

    @SerializedName("Cidade")
    private String city;

    @SerializedName("Numero")
    private String houseNumber;

    @SerializedName("Referencia")
    private String reference;

    @SerializedName("Cep")
    private String cep;

    @SerializedName("Uf")
    private String state;

    @SerializedName("Telefone")
    private String phone;

    @SerializedName("Celular")
    private String cellphone;

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

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getRgsAgency() {
        return rgsAgency;
    }

    public void setRgsAgency(String rgsAgency) {
        this.rgsAgency = rgsAgency;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getMothersName() {
        return mothersName;
    }

    public void setMothersName(String mothersName) {
        this.mothersName = mothersName;
    }

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(int maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }
}
