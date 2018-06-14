package com.vonergy.connection;

public class ConnectionConstants {

    public static final String ipv4 = "vonergyapi.azurewebsites.net";
    //public static final String ipv4 = "192.168.15.4:60529";

    private static final String http = "https://";
    private static final String api = "/api/";

    private static final String user = "Funcionario/";
    public static final String login = user + "Acessar";
    public static final String listUser = user + "listar";

    private static final String consumption = "Consumo/";
    public static final String consumptionInRealTime = consumption + "ConsumoReal";
    public static final String consumptionPerHour = consumption + "ConsumoHora";
    public static final String dailyConsumption = consumption + "ConsumoDiarioDicionario";
    public static final String weeklyConsumption = consumption + "ConsumoSemanal";
    public static final String monthlyConsumption = consumption + "ConsumoMesal";
    public static final String annualConsumption = consumption + "ConsumoAnual";

    private static final String device = "Equipamento/";
    public static final String listDevice = device + "listar";
    public static final String editDevice = device + "Alterar";

    public static String getServerUrl(String ip) {
        return http + ip + api;
    }
}
