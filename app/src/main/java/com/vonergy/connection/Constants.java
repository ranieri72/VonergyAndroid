package com.vonergy.connection;

public class Constants {

    public static final String ipv4 = "vonergyapi.azurewebsites.net";
    //public static final String ipv4 = "192.168.15.4:60529";

    private static final String http = "https://";
    //private static final String http = "http://";
    private static final String api = "/api/";

    public static final String login = "FuncionarioMobile/Acessar";

    private static final String consumption = "Consumo/";

    public static final String monthlyConsumption = consumption + "ListarConsumoMesal";
    public static final String dailyConsumption = consumption + "ListarConsumoDiario";
    public static final String consumptionPerHour = consumption + "ListarConsumoPorHora";

    public static String getServerUrl(String ip) {
        return http + ip + api;
    }
}
