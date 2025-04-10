package com.example.assessment2.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.assessment2.datastore.SettingsDataStore
import androidx.compose.ui.platform.LocalContext

@Composable
fun FinanceAppTheme(
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val settings = remember { SettingsDataStore(context) }

    val backgroundColor by settings.backgroundColor.collectAsState(initial = "White")
    val fontSizeSetting by settings.fontSize.collectAsState(initial = "Medium")

    val colors = when (backgroundColor) {
        "Gray" -> lightColorScheme(surface = Color(0xFFF5F5F5))
        "Blue" -> lightColorScheme(surface = Color(0xFFE3F2FD))
        else -> lightColorScheme(surface = Color.White)
    }

    // 动态调整字体大小
    val fontScale = when (fontSizeSetting) {
        "Small" -> 12.sp
        "Large" -> 20.sp
        else -> 16.sp
    }

    // 自定义 Typography
    val customTypography = Typography(
        bodyLarge = Typography().bodyLarge.copy(fontSize = fontScale),
        bodyMedium = Typography().bodyMedium.copy(fontSize = fontScale),
        bodySmall = Typography().bodySmall.copy(fontSize = fontScale)
    )

    // 应用主题
    MaterialTheme(
        colorScheme = colors,
        typography = customTypography,
        shapes = Shapes(),
        content = content
    )
}
