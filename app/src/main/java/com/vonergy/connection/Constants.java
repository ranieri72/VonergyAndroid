package com.vonergy.connection;

public class Constants {

    public static final String ipv4 = "192.168.15.4:60529";

    private static final String http = "http://";
    private static final String api = "/api/";

    public static final String login = "FuncionarioMobile/Acessar";

    private static final String consumption = "Consumo/";

    public static final String monthlyConsumption = consumption + "ListarConsumoEletronicaMesal";
    public static final String dailyConsumption = consumption + "ListarConsumoEletronicaDiaria";
    public static final String consumptionPerHour = consumption + "ListarConsumoEletronicaPorHora";

    public static String getServerUrl(String ip) {
        return http + ip + api;
    }
}
