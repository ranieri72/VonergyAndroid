package com.vonergy.connection;

public class Constants {

    public static final String ipv4 = "192.168.15.4:60529";

    private static final String http = "http://";
    private static final String api = "/api/";

    public static final String login = "FuncionarioMobile/Acessar";

    public static String getServerUrl(String ip) {
        return http + ip + api;
    }
}
