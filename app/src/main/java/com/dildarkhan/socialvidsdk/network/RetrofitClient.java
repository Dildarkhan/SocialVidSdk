package com.dildarkhan.socialvidsdk.network;

import android.content.Context;

import com.getkeepsafe.relinker.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient{
    private Retrofit retrofit;
    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();
    private static RetrofitClient instance = null;
    private static Api service = null;
    private static HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor();

    private RetrofitClient() {
        httpClient.interceptors().add(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder builder = originalRequest.newBuilder().
                        method(originalRequest.method(), originalRequest.body());
                okhttp3.Response response = chain.proceed(builder.build());
                /*
                Do what you want
                 */
                return response;
            }
        });

        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            // add logging as last interceptor
            httpClient.addInterceptor(logging);
        }

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder().client(httpClient.build()).
                baseUrl(Api.BASE_URL).
                addConverterFactory(GsonConverterFactory.create(gson)).build();
        service = retrofit.create(Api.class);
    }


    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public Api getApiService() {
        return service;
    }
}

/*

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private Api myApi;

    private RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myApi = retrofit.create(Api.class);
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public Api getMyApi() {
        return myApi;
    }
}
*/
