
package com.example.assessment2.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.assessment2.components.BottomBackBar
import com.example.assessment2.database.FinanceDatabase
import com.example.assessment2.datastore.BudgetDataStore
import kotlinx.coroutines.launch
import java.time.LocalDate

enum class ViewMode { Pie, Bar, Table }

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BudgetScreen(navController: NavController) {
    val context = LocalContext.current
    val db = FinanceDatabase.getDatabase(context)
    val dao = db.transactionDao()
    val scope = rememberCoroutineScope()

    val budgetStore = remember { BudgetDataStore(context) }

    var selectedRange by remember { mutableStateOf("Week") }
    val filterOptions = listOf("Week", "Month", "Year", "All")

    var weeklyInput by remember { mutableStateOf("") }
    var monthlyInput by remember { mutableStateOf("") }
    var yearlyInput by remember { mutableStateOf("") }

    val weeklyBudget by budgetStore.weeklyBudget.collectAsState(initial = 0.0)
    val monthlyBudget by budgetStore.monthlyBudget.collectAsState(initial = 0.0)
    val yearlyBudget by budgetStore.yearlyBudget.collectAsState(initial = 0.0)

    val allTransactions by dao.getAllTransactions().collectAsState(initial = emptyList())

    var currentView by remember { mutableStateOf(ViewMode.Table) }

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
            @OptIn(ExperimentalMaterial3Api::class) // 显式启用实验性 API
            TopAppBar(title = { Text("Budget Overview", style = MaterialTheme.typography.titleLarge) })
        },
        bottomBar = { BottomBackBar(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Set Budget:")
            OutlinedTextField(value = weeklyInput, onValueChange = { weeklyInput = it }, label = { Text("Weekly Budget") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = monthlyInput, onValueChange = { monthlyInput = it }, label = { Text("Monthly Budget") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = yearlyInput, onValueChange = { yearlyInput = it }, label = { Text("Yearly Budget") }, modifier = Modifier.fillMaxWidth())

            Button(onClick = {
                scope.launch {
                    weeklyInput.toDoubleOrNull()?.let { budgetStore.saveWeeklyBudget(it) }
                    monthlyInput.toDoubleOrNull()?.let { budgetStore.saveMonthlyBudget(it) }
                    yearlyInput.toDoubleOrNull()?.let { budgetStore.saveYearlyBudget(it) }
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
                                    Text("Date: $date", style = MaterialTheme.typography.bodyMedium)
                                    Text("Type: ${tx.type}", style = MaterialTheme.typography.bodyMedium)
                                    Text("Amount: \$${tx.amount}", style = MaterialTheme.typography.bodyMedium)
                                    Text("Reason: ${tx.reason}", style = MaterialTheme.typography.bodyMedium)
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
