package com.example.assessment2.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.assessment2.components.BottomBackBar
import android.widget.Toast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomeExpenseScreen(navController: NavController) {
    val context = LocalContext.current
    var type by remember { mutableStateOf("Income") }
    var amount by remember { mutableStateOf("") }
    var reason by remember { mutableStateOf("") }
    val reasonLabel = if (type == "Income") "Source" else "Purpose"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Track Income & Expenses", style = MaterialTheme.typography.bodyLarge) },
                modifier = Modifier.fillMaxWidth()
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Select Type:",
                    style = TextStyle(fontSize = MaterialTheme.typography.titleMedium.fontSize),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    RadioButton(
                        selected = type == "Income",
                        onClick = { type = "Income" },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text("Income", style = MaterialTheme.typography.bodyLarge)
                    RadioButton(
                        selected = type == "Expense",
                        onClick = { type = "Expense" },
                        modifier = Modifier.padding(start = 16.dp, end = 8.dp)
                    )
                    Text("Expense", style = MaterialTheme.typography.bodyLarge)
                }
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount", style = MaterialTheme.typography.bodyLarge) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                OutlinedTextField(
                    value = reason,
                    onValueChange = { reason = it },
                    label = { Text(reasonLabel, style = MaterialTheme.typography.bodyLarge) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                Button(
                    onClick = {
                        val value = amount.toDoubleOrNull()
                        if (value != null && value > 0) {
                            val actionText = if (type == "Income") "Deposit" else "Spend"
                            val finalReason = if (reason.isNotBlank()) reason else "unknown"
                            Toast.makeText(context, "$$value already ${actionText}，Reason is $finalReason", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add $type", style = MaterialTheme.typography.bodyLarge)
                }
            }
        },
        bottomBar = { BottomBackBar(navController) },
    )
}
