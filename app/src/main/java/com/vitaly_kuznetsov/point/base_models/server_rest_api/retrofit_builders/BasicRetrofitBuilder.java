package com.vitaly_kuznetsov.point.base_models.server_rest_api.retrofit_builders;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BasicRetrofitBuilder {

    private static Retrofit retrofit;
    private static final String BASE_URL =
            "https://point-backend.com";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
