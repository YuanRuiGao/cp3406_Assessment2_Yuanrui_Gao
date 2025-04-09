package com.example.assessment2.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Finance Dashboard") },
                colors = TopAppBarDefaults.smallTopAppBarColors()
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val buttonModifier = Modifier
                    .padding(12.dp)
                    .size(120.dp)

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row {
                        HomeButton("Income and Expenses", navController, "income_expense", buttonModifier)
                        HomeButton("Monthly Budget", navController, "budget", buttonModifier)
                    }
                    Row {
                        HomeButton("Manage Goals", navController, "goals", buttonModifier)
                        HomeButton("Bill Reminder", navController, "reminders", buttonModifier)
                    }
                    Row {
                        HomeButton("Suggest", navController, "suggestions", buttonModifier)
                        HomeButton("Settings", navController, "settings", buttonModifier)
                    }
                }
            }
        }
    )
}

@Composable
fun HomeButton(label: String, navController: NavController, route: String, modifier: Modifier) {
    Button(
        onClick = { navController.navigate(route) },
        modifier = modifier,
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
