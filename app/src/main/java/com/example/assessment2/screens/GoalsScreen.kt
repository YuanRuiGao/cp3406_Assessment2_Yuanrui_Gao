package com.example.assessment2.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.assessment2.components.BottomBackBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalsScreen(navController: NavController) {
    var goalName by remember { mutableStateOf("") }
    var goalAmount by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Savings Goals") }) },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                OutlinedTextField(
                    value = goalName,
                    onValueChange = { goalName = it },
                    label = { Text("Goal Name") }
                )
                OutlinedTextField(
                    value = goalAmount,
                    onValueChange = { goalAmount = it },
                    label = { Text("Goal Amount") }
                )
                Button(onClick = {
                    // Save goal
                }) {
                    Text("Add Goal")
                }
            }
        },
        bottomBar = { BottomBackBar(navController) },
    )
}
