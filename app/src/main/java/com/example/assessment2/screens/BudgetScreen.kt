
package com.example.assessment2.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.assessment2.components.BottomBackBar
import com.example.assessment2.database.FinanceDatabase
import com.example.assessment2.datastore.BudgetDataStore
import com.example.assessment2.viewmodel.budget.BudgetViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate


enum class ViewMode { Pie, Bar, Table }

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetScreen(navController: NavController, viewModel: BudgetViewModel = hiltViewModel()) {
    val scope = rememberCoroutineScope()
    var selectedRange by remember { mutableStateOf("Week") }
    val filterOptions = listOf("Week", "Month", "Year", "All")
    var currentView by remember { mutableStateOf(ViewMode.Table) }

    var weeklyInput by remember { mutableStateOf("") }
    var monthlyInput by remember { mutableStateOf("") }
    var yearlyInput by remember { mutableStateOf("") }

    val allTransactions by viewModel.allTransactions.collectAsState(initial = emptyList())
    val weeklyBudget by viewModel.weeklyBudget.collectAsState(initial = 0.0)
    val monthlyBudget by viewModel.monthlyBudget.collectAsState(initial = 0.0)
    val yearlyBudget by viewModel.yearlyBudget.collectAsState(initial = 0.0)

    val filteredTransactions = remember(allTransactions, selectedRange) {
        val now = LocalDate.now()
        val from = when (selectedRange) {
            "Week" -> now.minusDays(7)
            "Month" -> now.minusMonths(1)
            "Year" -> now.minusYears(1)
            else -> LocalDate.MIN
        }
        allTransactions.filter {
            val txDate = LocalDate.of(it.year, it.month, it.day)
            txDate >= from
        }
    }

    val totalIncome = filteredTransactions.filter { it.type == "Income" }.sumOf { it.amount }
    val totalExpense = filteredTransactions.filter { it.type == "Expense" }.sumOf { it.amount }
    val activeBudget = when (selectedRange) {
        "Week" -> weeklyBudget
        "Month" -> monthlyBudget
        "Year" -> yearlyBudget
        else -> Double.MAX_VALUE
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Budget Overview", style = MaterialTheme.typography.titleLarge) })
        },
        bottomBar = { BottomBackBar(navController) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("Set Budget:")
            OutlinedTextField(
                value = weeklyInput,
                onValueChange = { weeklyInput = it },
                label = { Text("Weekly Budget") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = monthlyInput,
                onValueChange = { monthlyInput = it },
                label = { Text("Monthly Budget") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = yearlyInput,
                onValueChange = { yearlyInput = it },
                label = { Text("Yearly Budget") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(onClick = {
                scope.launch {
                    viewModel.saveBudget(
                        weeklyInput.toDoubleOrNull() ?: 0.0,
                        monthlyInput.toDoubleOrNull() ?: 0.0,
                        yearlyInput.toDoubleOrNull() ?: 0.0
                    )
                }
            }, modifier = Modifier.padding(vertical = 8.dp)) {
                Text("Save Budget")
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text("Current Range: $selectedRange")
            Text("• Income Total: $totalIncome")
            Text("• Expense Total: $totalExpense", color = if (totalExpense > activeBudget) Color.Red else Color.Unspecified)
            if (selectedRange != "All") {
                val diff = activeBudget - totalExpense
                Text("• Budget Remaining: $diff", color = if (diff < 0) Color.Red else Color.Unspecified)
            }

            Spacer(modifier = Modifier.height(12.dp))
            var expanded by remember { mutableStateOf(false) }
            Box {
                Button(onClick = { expanded = true }) { Text("Filter: $selectedRange") }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    filterOptions.forEach { range ->
                        DropdownMenuItem(text = { Text(range) }, onClick = {
                            selectedRange = range
                            expanded = false
                        })
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Row {
                Button(onClick = { currentView = ViewMode.Table }) { Text("Show Table") }
                Spacer(Modifier.width(8.dp))
                Button(onClick = { currentView = ViewMode.Bar }) { Text("BarChart") }
                Spacer(Modifier.width(8.dp))
                Button(onClick = { currentView = ViewMode.Pie }) { Text("PieChart") }
            }

            Spacer(modifier = Modifier.height(16.dp))
            when (currentView) {
                ViewMode.Table -> {
                    if (filteredTransactions.isEmpty()) {
                        Text("No transactions available.")
                    } else {
                        LazyColumn {
                            items(filteredTransactions) { tx ->
                                val date = "%04d-%02d-%02d".format(tx.year, tx.month, tx.day)
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                                    shape = MaterialTheme.shapes.medium
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text("Date: $date")
                                        Text("Type: ${tx.type}")
                                        Text("Amount: \$${tx.amount}")
                                        Text("Reason: ${tx.reason}")
                                    }
                                }
                            }
                        }
                    }
                }
                ViewMode.Bar -> BudgetBarChart(totalIncome, totalExpense)
                ViewMode.Pie -> BudgetPieChart(totalIncome, totalExpense)
            }
        }
    }
}
