package com.example.assessment2.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val api: ExchangeRateApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.exchangerate.host/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExchangeRateApi::class.java)
    }
}
