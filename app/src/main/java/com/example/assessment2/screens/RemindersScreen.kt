
package com.example.assessment2.screens

import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.assessment2.model.Reminder
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.assessment2.viewmodel.reminder.ReminderViewModel


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: ReminderViewModel = hiltViewModel()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    var reminderName by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var frequency by remember { mutableStateOf("Weekly") }
    val reminders by viewModel.allReminders.collectAsState()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, day -> selectedDate = LocalDate.of(year, month + 1, day) },
        selectedDate.year, selectedDate.monthValue - 1, selectedDate.dayOfMonth
    )

    val today = LocalDate.now()
    val displayText = when (frequency) {
        "Weekly" -> {
            val next = selectedDate.with(selectedDate.dayOfWeek).plusWeeks(1)
            val daysUntil = ChronoUnit.DAYS.between(today, next).toInt()-7
            "$daysUntil days left until next bill(${selectedDate.dayOfWeek}）"
        }
        "Monthly" -> {
            val nextMonth = selectedDate.plusMonths(1)
            "Next billing date is：${nextMonth.format(formatter)}"
        }
        else -> ""
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Bill Reminders", style = MaterialTheme.typography.bodyLarge) })
        },
        bottomBar = { BottomBackBar(navController) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            OutlinedTextField(
                value = reminderName,
                onValueChange = { reminderName = it },
                label = { Text("Bill Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text("Due Date: ${selectedDate.format(formatter)}")
            Button(onClick = { datePickerDialog.show() }) {
                Text("Pick Date")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Reminder Frequency")
            Row {
                RadioButton(selected = frequency == "Weekly", onClick = { frequency = "Weekly" })
                Text("Weekly", modifier = Modifier.padding(end = 16.dp))
                RadioButton(selected = frequency == "Monthly", onClick = { frequency = "Monthly" })
                Text("Monthly")
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(displayText)

            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                if (reminderName.isNotBlank()) {
                    val reminder = Reminder(
                        name = reminderName,
                        date = selectedDate.toString(),
                        frequency = frequency
                    )
                    viewModel.insertReminder(reminder)
                    reminderName = ""
                }
            }) {
                Text("Add Reminder")
            }

            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(reminders) { reminder ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF1F3)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(reminder.name, fontWeight = FontWeight.Bold)
                                Text("Due: ${reminder.date}")
                                Text("Frequency: ${reminder.frequency}")
                            }
                            IconButton(onClick = {
                                viewModel.deleteReminder(reminder)
                            }) {
                                Text("✕", color = Color.Red, fontSize = 20.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
