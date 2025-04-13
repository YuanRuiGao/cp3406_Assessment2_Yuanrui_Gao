package com.example.assessment2

import com.example.assessment2.api.ExchangeRateApi
import com.example.assessment2.api.ExchangeRateResponse
import com.example.assessment2.viewmodel.suggest.SuggestViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class SuggestViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var api: ExchangeRateApi

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        api = mock()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test exchangeInfo updates on success`() = runTest {
        val fakeResponse = ExchangeRateResponse(
            success = true,
            source = "USD",
            quotes = mapOf("USDAUD" to 1.5)
        )
        whenever(api.getRates(any())).thenReturn(fakeResponse)

        val viewModel = SuggestViewModel(api)
        viewModel.fetchExchangeRates() // 👈 必须手动触发
        advanceUntilIdle()

        val expected = listOf("USD → AUD: 1.5", "Tip: Good time to exchange.")
        org.junit.Assert.assertEquals(expected, viewModel.exchangeInfo.first())
    }

    @Test
    fun `test exchangeInfo updates on failure`() = runTest {
        whenever(api.getRates(any())).thenThrow(RuntimeException("API error"))

        val viewModel = SuggestViewModel(api)
        viewModel.fetchExchangeRates() // 👈 必须手动触发
        advanceUntilIdle()

        val expected = listOf("Failed to load exchange rate.")
        org.junit.Assert.assertEquals(expected, viewModel.exchangeInfo.first())
    }
}
