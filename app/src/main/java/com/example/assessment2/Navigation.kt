package com.example.assessment2

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.assessment2.screens.*

@ExperimentalMaterial3Api
@Composable
fun FinanceNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("income_expense") { IncomeExpenseScreen(navController) }
        composable("budget") { BudgetScreen(navController) }
        composable("goals") { GoalsScreen(navController) }
        composable("reminders") { RemindersScreen(navController) }
        composable("suggest") { SuggestScreen(navController) }
        composable("settings") { SettingsScreen(navController) }
    }
}
