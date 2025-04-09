package com.example.assessment2.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.assessment2.components.BottomBackBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuggestionsScreen(navController: NavController) {
    val suggestions = listOf(
        "Cancel unused subscriptions",
        "Cook more at home instead of dining out",
        "Set a weekly spending limit",
        "Review your monthly streaming plans"
    )

    Scaffold(
        topBar = { TopAppBar(title = { Text("Saving Suggestions") }) },
        content = { paddingValues: PaddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) { // 使用 Modifier.padding 应用 paddingValues
                Text("Here are some ways to save:")
                suggestions.forEach { suggestion ->
                    Text("• $suggestion")
                }
            }
        },
        bottomBar = { BottomBackBar(navController) },
    )
}
