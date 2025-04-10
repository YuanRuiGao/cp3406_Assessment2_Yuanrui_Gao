package com.example.assessment2.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.navigation.NavController
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import com.example.assessment2.components.BottomBackBar

@Composable
@OptIn(ExperimentalMaterial3Api::class) // 声明启用实验性 API
fun BudgetScreen(navController: NavController) {
    var budget by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Monthly Budget",style = MaterialTheme.typography.bodyLarge) }) },
        content = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) { // 应用 innerPadding
                OutlinedTextField(
                    value = budget,
                    onValueChange = { budget = it },
                    label = { Text("Set Monthly Budget",style = MaterialTheme.typography.bodyLarge) }
                )
                Button(onClick = {
                    // Save budget
                }) {
                    Text("Save",style = MaterialTheme.typography.bodyLarge)
                }
            }
        },
        bottomBar = { BottomBackBar(navController) },

    )
}
