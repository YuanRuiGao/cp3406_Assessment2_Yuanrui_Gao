package com.example.assessment2.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.assessment2.components.BottomBackBar
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.assessment2.viewmodel.suggest.SuggestViewModel



@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SuggestScreen(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()

    val viewModel: SuggestViewModel = hiltViewModel()
    val exchangeInfo by viewModel.exchangeInfo.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Smart Suggestions", style = MaterialTheme.typography.bodyLarge) }
            )
        },
        bottomBar = { BottomBackBar(navController) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            item {
                SuggestionCard(
                    title = "This Week's Summary",
                    content = listOf(
                        "You've spent 82% of your weekly budget.",
                        "Entertainment category exceeded by \$50.",
                        "Savings goal is 60% complete."
                    )
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                SuggestionCard(
                    title = "Currency Rate Alert",
                    content = exchangeInfo
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                SuggestionCard(
                    title = "General Advice",
                    content = listOf(
                        "You tend to overspend on weekends.",
                        "Consider setting a daily spending limit."
                    )
                )
            }
        }
    }
}

@Composable
fun SuggestionCard(title: String, content: List<String>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            content.forEach {
                Text("• $it", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
