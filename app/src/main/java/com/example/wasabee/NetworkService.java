package com.example.wasabee;

import android.content.Context;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class NetworkService {
    private static NetworkService mInstance;
    private static final String BASE_URL = "http://192.168.1.141:8080";
    private Retrofit mRetrofit;

    private Context context;

    private NetworkService(Context context) {
        OkHttpClient.Builder okhttpBuilder = new OkHttpClient().newBuilder().addInterceptor(new TokenInterceptor(context));
        OkHttpClient client = okhttpBuilder.build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static NetworkService getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NetworkService(context);
        }
        return mInstance;
    }

    public JSONPlaceHolderAPI getJSONApi() {
        return mRetrofit.create(JSONPlaceHolderAPI.class);
    }
}