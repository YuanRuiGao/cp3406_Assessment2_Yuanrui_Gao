package com.example.assessment2.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Finance Dashboard", style = MaterialTheme.typography.bodyLarge) },
                colors = TopAppBarDefaults.smallTopAppBarColors()
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Welcome!", style = MaterialTheme.typography.bodyLarge)

                val buttonModifier = Modifier
                    .padding(12.dp)
                    .size(130.dp)

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row {
                        HomeButton("Track", navController, "income_expense", buttonModifier)
                        HomeButton("Budget", navController, "budget", buttonModifier)
                    }
                    Row {
                        HomeButton("Goals", navController, "goals", buttonModifier)
                        HomeButton("Remind", navController, "reminders", buttonModifier)
                    }
                    Row {
                        HomeButton("Suggest", navController, "suggest", buttonModifier)
                        HomeButton("Setting", navController, "settings", buttonModifier)
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
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
