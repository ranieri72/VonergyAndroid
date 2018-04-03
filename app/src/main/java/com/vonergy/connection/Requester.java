package com.vonergy.connection;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.vonergy.util.Util.ipv4;

public class Requester {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient client = new OkHttpClient();

    public String post(String api, String json) throws IOException {
        String serverUrl = Constants.getServerUrl(ipv4) + api;

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(serverUrl)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        assert response.body() != null;
        return response.body().string();
    }
}
