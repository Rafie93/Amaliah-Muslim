package com.basantandevloper.geo.muslimamaliyah.API;

import com.basantandevloper.geo.muslimamaliyah.utils.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {


    public static Retrofit retrofit = null;
    public static Retrofit retrofitKemeneg = null;
    public static Retrofit retrofitSheet = null;
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL_MUSLIM)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    public static Retrofit getClientQuran() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL_QURAN)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    public static Retrofit getClientKemeneg() {
        if (retrofitKemeneg == null) {
            retrofitKemeneg = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL_KEMENEG)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofitKemeneg;
    }
    public static Retrofit getClientSheet() {

        if (retrofitSheet == null) {
            retrofitSheet = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL_GOOGLE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofitSheet;
    }


}
