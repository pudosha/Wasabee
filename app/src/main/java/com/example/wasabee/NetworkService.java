package com.example.wasabee;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService mInstance;
    private static final String BASE_URL = "http://192.168.0.135:8080";
    private Retrofit mRetrofit;

    private NetworkService() {
        OkHttpClient.Builder okhttpBuilder = new OkHttpClient().newBuilder().addInterceptor(new TokenInterceptor());
        OkHttpClient client = okhttpBuilder.build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public JSONPlaceHolderAPI getJSONApi() {
        return mRetrofit.create(JSONPlaceHolderAPI.class);
    }
}