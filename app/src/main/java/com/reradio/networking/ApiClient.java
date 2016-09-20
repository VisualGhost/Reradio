package com.reradio.networking;

import com.reradio.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private ApiClient() {
        // private constructor
    }

    private static class RetrofitHolder {
        private static Retrofit sRetrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static Retrofit getClient() {
        return RetrofitHolder.sRetrofit;
    }
}
