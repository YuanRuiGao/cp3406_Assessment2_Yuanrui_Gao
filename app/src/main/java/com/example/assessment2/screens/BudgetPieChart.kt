
package com.example.assessment2.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BudgetPieChart(income: Double, expense: Double) {
    val total = income + expense
    val incomeSweep = if (total > 0) 360f * (income / total).toFloat() else 0f

    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Text("Pie Chart")
        Canvas(modifier = Modifier
            .size(200.dp)
            .padding(8.dp)) {
            drawArc(
                color = Color.Green,
                startAngle = 0f,
                sweepAngle = incomeSweep,
                useCenter = true,
                size = Size(size.width, size.height)
            )
            drawArc(
                color = Color.Red,
                startAngle = incomeSweep,
                sweepAngle = 360f - incomeSweep,
                useCenter = true,
                size = Size(size.width, size.height)
            )
        }
        Text("Green = Income, Red = Expense")
    }
}
