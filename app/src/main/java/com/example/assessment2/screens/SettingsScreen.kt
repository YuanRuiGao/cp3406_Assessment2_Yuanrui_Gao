package com.example.assessment2.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.assessment2.components.BottomBackBar
import com.example.assessment2.components.DropdownMenuSetting
import com.example.assessment2.datastore.SettingsDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    val context = LocalContext.current
    val settings = remember { SettingsDataStore(context) }

    val selectedColor by settings.backgroundColor.collectAsState(initial = "White")
    val selectedFontSize by settings.fontSize.collectAsState(initial = "Medium")

    Scaffold(
        topBar = { TopAppBar(title = { Text("Settings") }) },
        bottomBar = { BottomBackBar(navController) },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Text("Background Color")
                DropdownMenuSetting(
                    options = listOf("White", "Gray", "Blue"),
                    selectedOption = selectedColor,
                    onOptionSelected = {
                        CoroutineScope(Dispatchers.IO).launch {
                            settings.saveBackgroundColor(it)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text("Font Size")
                DropdownMenuSetting(
                    options = listOf("Small", "Medium", "Large"),
                    selectedOption = selectedFontSize,
                    onOptionSelected = {
                        CoroutineScope(Dispatchers.IO).launch {
                            settings.saveFontSize(it)
                        }
                    }
                )
            }
        }
    )
}
