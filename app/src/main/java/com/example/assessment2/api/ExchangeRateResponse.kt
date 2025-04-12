package com.example.assessment2.api

data class ExchangeRateResponse(
    val success: Boolean,
    val source: String,
    val quotes: Map<String, Double>? // 注意这个是 Map<String, Double>?（可空）
)