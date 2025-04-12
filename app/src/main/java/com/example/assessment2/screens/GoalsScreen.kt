package com.example.assessment2.screens

import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.assessment2.components.BottomBackBar
import com.example.assessment2.database.FinanceDatabase
import com.example.assessment2.model.Goal
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.text.SimpleDateFormat
import java.util.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.assessment2.viewmodel.GoalsViewModel




@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalsScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: GoalsViewModel = hiltViewModel()

    val allGoals by viewModel.allGoals.collectAsState(initial = emptyList())
    val allTransactions by viewModel.allTransactions.collectAsState(initial = emptyList())

    val incomeTransactions = allTransactions.filter { it.type == "Income" }

    val (completedGoalIds, totalNeeded) = remember(incomeTransactions, allGoals) {
        derivedStateOf {
            val sortedGoals = allGoals.sortedBy { it.createdAt }
            val sortedIncomes = incomeTransactions.sortedBy { it.date }

            var incomePool = sortedIncomes.toMutableList()
            val completedIds = mutableSetOf<Int>()

            for (goal in sortedGoals) {
                val incomeAfter = incomePool.filter { it.date >= goal.createdAt }
                var sum = 0.0
                val usedIncomes = mutableListOf<com.example.assessment2.model.Transaction>()

                for (income in incomeAfter) {
                    sum += income.amount
                    usedIncomes.add(income)
                    if (sum >= goal.amount) break
                }

                if (sum >= goal.amount) {
                    completedIds.add(goal.id)
                    incomePool.removeAll(usedIncomes)
                }
            }

            Pair(completedIds.toList(), sortedGoals.filterNot { it.id in completedIds }.sumOf { it.amount })
        }
    }.value

    var goalName by remember { mutableStateOf("") }
    var goalAmount by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, day -> selectedDate = LocalDate.of(year, month + 1, day) },
        selectedDate.year, selectedDate.monthValue - 1, selectedDate.dayOfMonth
    )

    Scaffold(
        topBar = { TopAppBar(title = { Text("Savings Goals") }) },
        bottomBar = { BottomBackBar(navController) }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            OutlinedTextField(value = goalName, onValueChange = { goalName = it }, label = { Text("Goal Name") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = goalAmount, onValueChange = { goalAmount = it }, label = { Text("Goal Amount") }, modifier = Modifier.fillMaxWidth())
            Text("Target Date: $selectedDate")
            Button(onClick = { datePickerDialog.show() }) { Text("Pick Deadline") }

            Spacer(Modifier.height(8.dp))
            Button(onClick = {
                val amount = goalAmount.toDoubleOrNull() ?: 0.0
                if (goalName.isNotBlank() && amount > 0) {
                    viewModel.addGoal(goalName, amount, selectedDate.toString())
                    goalName = ""
                    goalAmount = ""
                }
            }) {
                Text("Add Goal")
            }

            Spacer(Modifier.height(16.dp))
            Text("💰 Total Needed: \$$totalNeeded", color = Color.Blue, fontWeight = FontWeight.Bold)

            LazyColumn {
                items(allGoals) { goal ->
                    val isCompleted = goal.id in completedGoalIds
                    val isOverdue = LocalDate.now() > LocalDate.parse(goal.deadline) && !isCompleted
                    val color = when {
                        isCompleted -> Color.Green
                        isOverdue -> Color.Red
                        else -> MaterialTheme.colorScheme.onSurface
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Save \$${goal.amount} for ${goal.name} before ${goal.deadline}", fontWeight = FontWeight.Bold, color = color)
                            }
                            IconButton(onClick = { viewModel.deleteGoal(goal) }) {
                                Text("✕", color = Color.Red, fontSize = 20.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
