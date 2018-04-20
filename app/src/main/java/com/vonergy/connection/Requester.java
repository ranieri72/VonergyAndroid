package com.vonergy.connection;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.vonergy.util.Util.ipv4;

public class Requester {

    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client;

    public Requester() {
        client = new OkHttpClient();
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS)
                .build();
    }

    public String post(String api, String json) throws IOException {
        String serverUrl = Constants.getServerUrl(ipv4) + api;
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(serverUrl).post(body).build();
        return client.newCall(request).execute().body().string();
    }

    public String get(String api) throws IOException {
        String serverUrl = Constants.getServerUrl(ipv4) + api;
        Request request = new Request.Builder().url(serverUrl).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
