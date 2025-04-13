package com.example.assessment2.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.assessment2.components.BottomBackBar
import com.example.assessment2.components.DropdownMenuSetting
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.assessment2.viewmodel.setting.SettingsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    val viewModel: SettingsViewModel = hiltViewModel()
    val selectedColor by viewModel.backgroundColor.collectAsState(initial = "White")
    val selectedFontSize by viewModel.fontSize.collectAsState(initial = "Medium")

    Scaffold(
        topBar = { TopAppBar(title = { Text("Settings") }) },
        bottomBar = { BottomBackBar(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Background Color")
            DropdownMenuSetting(
                options = listOf("White", "Gray", "Blue"),
                selectedOption = selectedColor,
                onOptionSelected = { viewModel.saveBackgroundColor(it) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("Font Size")
            DropdownMenuSetting(
                options = listOf("Small", "Medium", "Large"),
                selectedOption = selectedFontSize,
                onOptionSelected = { viewModel.saveFontSize(it) }
            )
        }
    }
}