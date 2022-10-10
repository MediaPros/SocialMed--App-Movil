package com.mediapros.socialmed;

import com.mediapros.socialmed.errors.network.ReportService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    public static Retrofit build() {
        return new Retrofit.Builder()
                .baseUrl("https://mediapros-socialmed-api.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
