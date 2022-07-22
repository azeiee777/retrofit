package com.aziztraders.retrofitlibrary;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkObjects {

    public static Retrofit getRetrofit() {
        return createRetrofitObject();
    }

    public static ApiCalls getApiCalls(){
        return getRetrofit().create(ApiCalls.class);
    }

    private static Retrofit createRetrofitObject() {
        Retrofit retro = new Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retro;
    }
}
