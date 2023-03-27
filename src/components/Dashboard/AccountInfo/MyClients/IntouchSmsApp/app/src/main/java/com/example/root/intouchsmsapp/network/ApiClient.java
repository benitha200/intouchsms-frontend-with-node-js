package com.example.root.intouchsmsapp.network;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    //public static final String BASE_URL = "http://192.168.43.143:8000/api/"; //Willam's computer
    //public static final String BASE_URL = "http://192.168.43.157:8000/api/"; //edwin's computer
    //public static final String BASE_URL = "http://intouchsms.co.rw:8000/api/"; //test production
    public static final String BASE_URL = "https://intouchsms.co.rw/api/"; //production
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}
