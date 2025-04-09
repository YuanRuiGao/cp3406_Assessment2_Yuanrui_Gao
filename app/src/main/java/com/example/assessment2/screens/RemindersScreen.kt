package com.example.assessment2.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.assessment2.components.BottomBackBar

@Composable
@OptIn(ExperimentalMaterial3Api::class) // 添加 OptIn 注解
fun RemindersScreen(navController: NavController) {
    var reminderName by remember { mutableStateOf("") }
    var reminderDate by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bill Reminders") },
                modifier = Modifier.fillMaxWidth() // 确保标题占满宽度
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = reminderName,
                    onValueChange = { reminderName = it },
                    label = { Text("Bill Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = reminderDate,
                    onValueChange = { reminderDate = it },
                    label = { Text("Due Date") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (reminderName.isNotEmpty() && reminderDate.isNotEmpty()) {
                            println("Reminder saved: $reminderName, Due Date: $reminderDate")
                            reminderName = ""
                            reminderDate = ""
                        } else {
                            println("Both fields are required.")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Reminder")
                }
            }
        },
        bottomBar = { BottomBackBar(navController) },
    )
}
