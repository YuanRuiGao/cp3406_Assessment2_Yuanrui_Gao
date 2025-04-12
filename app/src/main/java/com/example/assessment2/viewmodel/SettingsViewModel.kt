package com.example.assessment2.viewmodel.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assessment2.datastore.SettingsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    val fontSize = settingsDataStore.fontSize
    val backgroundColor = settingsDataStore.backgroundColor

    fun saveFontSize(size: String) {
        viewModelScope.launch {
            settingsDataStore.saveFontSize(size)
        }
    }

    fun saveBackgroundColor(color: String) {
        viewModelScope.launch {
            settingsDataStore.saveBackgroundColor(color)
        }
    }
}

