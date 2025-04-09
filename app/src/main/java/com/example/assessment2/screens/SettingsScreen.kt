package com.example.assessment2.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.assessment2.components.BottomBackBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Settings") })
        },
        bottomBar = { BottomBackBar(navController) },
        content = { padding ->
            Column(modifier = Modifier
                .padding(padding)
                .padding(16.dp)) {

                Text("Settings will go here.")

                // 示例设置项
                Row(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text("Currency:")
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("USD")
                }

                Row(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text("Night mode:")
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Off")
                }
            }
        },
    )
}