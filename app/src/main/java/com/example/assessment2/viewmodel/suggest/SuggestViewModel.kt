package com.example.assessment2.viewmodel.suggest

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assessment2.api.RetrofitClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuggestViewModel @Inject constructor() : ViewModel() {

    private val _exchangeInfo = MutableStateFlow<List<String>>(listOf("Loading..."))
    val exchangeInfo: StateFlow<List<String>> = _exchangeInfo

    init {
        fetchExchangeRates()
    }

    private fun fetchExchangeRates() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getRates("你的API Key")
                val quotes = response.quotes
                if (quotes?.containsKey("USDAUD") == true) {
                    val rate = quotes["USDAUD"]!!
                    _exchangeInfo.value = listOf("USD → AUD: $rate", "Tip: Good time to exchange.")
                } else {
                    _exchangeInfo.value = listOf("Exchange rate unavailable.")
                }
            } catch (e: Exception) {
                _exchangeInfo.value = listOf("Failed to load exchange rate.")
            }
        }
    }
}
