package com.example.assessment2

import com.example.assessment2.datastore.SettingsDataStore
import com.example.assessment2.viewmodel.setting.SettingsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {

    private lateinit var settingsDataStore: SettingsDataStore
    private lateinit var viewModel: SettingsViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        settingsDataStore = mock()
        viewModel = SettingsViewModel(settingsDataStore)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test saveFontSize calls settingsDataStore`() = runTest {
        val size = "Large"
        viewModel.saveFontSize(size)
        verify(settingsDataStore).saveFontSize(size)
    }

    @Test
    fun `test saveBackgroundColor calls settingsDataStore`() = runTest {
        val color = "Blue"
        viewModel.saveBackgroundColor(color)
        verify(settingsDataStore).saveBackgroundColor(color)
    }
}
