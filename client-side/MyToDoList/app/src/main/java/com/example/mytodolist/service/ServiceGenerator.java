package com.example.mytodolist.service;

import android.content.Context;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    public static final String BASE_URL = "http://10.0.2.2:8080/api/v1/";

    private static final long CONNECT_TIMEOUT = 30;
    private static final long READ_TIMEOUT = 30;
    private static final long WRITE_TIMEOUT = 30;

    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()));

    private static Retrofit retrofit = builder.build();

    private static OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

    public static <S> S createService(Context context, Class<S> serviceClass) {

        httpClientBuilder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        builder.client(httpClientBuilder.build());
        retrofit = builder.build();
        return retrofit.create(serviceClass);
    }
}
