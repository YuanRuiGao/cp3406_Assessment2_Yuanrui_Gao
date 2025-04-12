package com.example.assessment2.api

import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateApi {
    @GET("live")
    suspend fun getRates(
        @Query("access_key") accessKey: String
    ): ExchangeRateResponse
}
