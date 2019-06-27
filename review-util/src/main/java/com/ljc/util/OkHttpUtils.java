package com.ljc.util;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class OkHttpUtils {
    private static Logger logger = LoggerFactory.getLogger(OkHttpUtils.class);

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static OkHttpClient client = null;

    public static String doGet(String url) {
        OkHttpClient client = getClient();
        Request request = new Request.Builder().url(url)
                .get()
                .addHeader("Connection","close")
                .build();
        return execute(request, client);
    }

    public static String doGet(String url, long timeout) {
        OkHttpClient client = getClient(timeout);
        Request request = new Request.Builder().url(url)
                .get()
                .addHeader("Connection","close")
                .build();
        return execute(request, client);
    }

    public static String doPost(String url, String content) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), content);
        OkHttpClient client = getClient();
        Request request = new Request.Builder().url(url)
                .post(requestBody)
                .addHeader("Connection", "close")
                .addHeader("Content-Type", "application/json")
                .build();
        return execute(request, client);
    }

    public static void syncGet(String url) {
        OkHttpClient client = getClient();
        Request request = new Request.Builder().url(url)
                .get()
                .build();

        try {
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    logger.error("回调异常", e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response != null && response.body() != null) {
                        String result = response.body().string();
                    }
                }
            });
        } catch (Exception e) {
            logger.error("网络异常", e);
        }
    }

    public static OkHttpClient getClient() {
        if (client == null) {
            client = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();
        }
        return client;
    }
    
    public static OkHttpClient getClient(long timeout) {
    	OkHttpClient client1 = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(timeout, TimeUnit.SECONDS)
                    .readTimeout(timeout, TimeUnit.SECONDS)
                    .writeTimeout(timeout, TimeUnit.SECONDS)
                    .build();
        return client1;
    }

    private static String execute(Request request, OkHttpClient client) {
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    return responseBody.string();
                }
            }
        } catch (Exception e) {
            logger.info("网络异常", e);
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return "";
    }
}
