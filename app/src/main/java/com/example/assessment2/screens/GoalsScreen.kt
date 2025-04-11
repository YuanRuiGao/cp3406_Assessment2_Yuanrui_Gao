
package com.example.assessment2.screens

import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.assessment2.components.BottomBackBar
import com.example.assessment2.database.FinanceDatabase
import com.example.assessment2.database.GoalDao
import com.example.assessment2.model.Goal
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalsScreen(navController: NavController) {
    val context = LocalContext.current
    val db = FinanceDatabase.getDatabase(context)
    val dao: GoalDao = db.goalDao()
    val coroutineScope = rememberCoroutineScope()

    var goalName by remember { mutableStateOf("") }
    var goalAmount by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    val allGoals by dao.getAllGoals().collectAsState(initial = emptyList())
    val totalIncome by db.transactionDao().getAllTransactions().collectAsState(initial = emptyList())

    val totalSaved by remember(totalIncome) {
        derivedStateOf {
            totalIncome.filter { it.type == "Income" }.sumOf { it.amount }
        }
    }

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, day ->
            selectedDate = LocalDate.of(year, month + 1, day)
        },
        selectedDate.year,
        selectedDate.monthValue - 1,
        selectedDate.dayOfMonth
    )

    Scaffold(
        topBar = { TopAppBar(title = { Text("Savings Goals") }) },
        bottomBar = { BottomBackBar(navController) }
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .padding(16.dp)) {

            OutlinedTextField(
                value = goalName,
                onValueChange = { goalName = it },
                label = { Text("Goal Name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = goalAmount,
                onValueChange = { goalAmount = it },
                label = { Text("Goal Amount") },
                modifier = Modifier.fillMaxWidth()
            )
            Text("Target Date: $selectedDate")
            Button(onClick = { datePickerDialog.show() }) {
                Text("Pick Deadline")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                val amount = goalAmount.toDoubleOrNull() ?: 0.0
                if (goalName.isNotBlank() && amount > 0) {
                    coroutineScope.launch {
                        dao.insertGoal(Goal(name = goalName, amount = amount, deadline = selectedDate.toString()))
                        goalName = ""
                        goalAmount = ""
                    }
                }
            }) {
                Text("Add Goal")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("💰 Total Saved: \$$totalSaved", color = Color.Blue, fontWeight = FontWeight.Bold)

            totalIncome.forEach {
                Text("Record → ${it.type}: \$${it.amount}, reason=${it.reason}", fontSize = 12.sp, color = Color.Gray)
            }

            LazyColumn {
                items(allGoals) { goal ->
                    val deadline = LocalDate.parse(goal.deadline)
                    val goalReached = totalSaved >= goal.amount
                    val isOverdue = LocalDate.now() > deadline && !goalReached
                    val color = when {
                        goalReached -> Color.Green
                        isOverdue -> Color.Red
                        else -> Color.Unspecified
                    }
                    Text(
                        "Save \$${goal.amount} ${goal.name} before ${goal.deadline}",
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                        color = color
                    )

                }
            }
        }
    }
}
