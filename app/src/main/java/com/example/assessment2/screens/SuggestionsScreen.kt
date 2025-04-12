package com.example.assessment2.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.assessment2.components.BottomBackBar
import com.example.assessment2.api.RetrofitClient
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SuggestScreen(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()

    var exchangeInfo by remember { mutableStateOf<List<String>>(listOf("Loading...")) }

    // 加载汇率数据
    LaunchedEffect(true) {
        coroutineScope.launch {
            try {
                val response = RetrofitClient.api.getRates("66cdce41931dadb6a4f436b24826da82")
                val quotes = response.quotes
                if (quotes != null && quotes.containsKey("USDAUD")) {
                    val rate = quotes["USDAUD"] ?: 0.0
                    exchangeInfo = listOf("USD → AUD: $rate", "Tip: Good time to exchange.")
                } else {
                    Log.e("API_ERROR", "Quotes is null or missing USDAUD")
                    exchangeInfo = listOf("Exchange rate data unavailable.")
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Exception: ${e.message}", e)
                exchangeInfo = listOf("Failed to load exchange rate.")
            }
        }
    }

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
