package com.example.assessment2.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BudgetBarChart(income: Double, expense: Double) {
    val maxValue = maxOf(income, expense).coerceAtLeast(1.0)
    val incomeRatio = income / maxValue
    val expenseRatio = expense / maxValue

    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Text("Bar Chart")
        Canvas(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)) {
            val barWidth = size.width / 4

            drawRoundRect(
                color = Color.Green,
                topLeft = Offset(0f, size.height * (1 - incomeRatio).toFloat()),
                size = androidx.compose.ui.geometry.Size(barWidth, size.height * incomeRatio.toFloat()),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(10f, 10f)
            )

            drawRoundRect(
                color = Color.Red,
                topLeft = Offset(barWidth * 2, size.height * (1 - expenseRatio).toFloat()),
                size = androidx.compose.ui.geometry.Size(barWidth, size.height * expenseRatio.toFloat()),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(10f, 10f)
            )
        }
        Text("Income, Expense")
    }
}
