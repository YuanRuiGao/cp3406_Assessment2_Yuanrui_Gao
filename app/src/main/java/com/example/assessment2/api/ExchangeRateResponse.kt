package com.example.assessment2.api

data class ExchangeRateResponse(
    val success: Boolean,
    val source: String,
    val quotes: Map<String, Double>?
)