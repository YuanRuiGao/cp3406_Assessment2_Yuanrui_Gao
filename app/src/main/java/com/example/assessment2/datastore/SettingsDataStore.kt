package com.example.assessment2.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

// 使用 preferencesDataStore 扩展函数创建 DataStore 实例
val Context.settingsDataStore by preferencesDataStore(name = "app_settings")

// 定义存储键的常量对象
object SettingsKeys {
    val BACKGROUND_COLOR = stringPreferencesKey("background_color")
    val FONT_SIZE = stringPreferencesKey("font_size")
}

// 数据存储类
class SettingsDataStore(private val context: Context) {

    // 获取背景颜色，默认值为 "White"
    val backgroundColor = context.settingsDataStore.data.map { preferences ->
        preferences[SettingsKeys.BACKGROUND_COLOR] ?: "White"
    }

    // 获取字体大小，默认值为 "Medium"
    val fontSize = context.settingsDataStore.data.map { preferences ->
        preferences[SettingsKeys.FONT_SIZE] ?: "Medium"
    }

    // 保存背景颜色
    suspend fun saveBackgroundColor(color: String) {
        context.settingsDataStore.edit { settings ->
            settings[SettingsKeys.BACKGROUND_COLOR] = color
        }
    }

    // 保存字体大小
    suspend fun saveFontSize(size: String) {
        context.settingsDataStore.edit { settings ->
            settings[SettingsKeys.FONT_SIZE] = size
        }
    }
}
