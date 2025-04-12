package com.example.assessment2.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

val Context.budgetDataStore by preferencesDataStore(name = "budget_settings")

object BudgetKeys {
    val WEEKLY_BUDGET = doublePreferencesKey("weekly_budget")
    val MONTHLY_BUDGET = doublePreferencesKey("monthly_budget")
    val YEARLY_BUDGET = doublePreferencesKey("yearly_budget")
}

class BudgetDataStore(private val context: Context) {
    val weeklyBudget = context.budgetDataStore.data.map { it[BudgetKeys.WEEKLY_BUDGET] ?: 0.0 }
    val monthlyBudget = context.budgetDataStore.data.map { it[BudgetKeys.MONTHLY_BUDGET] ?: 0.0 }
    val yearlyBudget = context.budgetDataStore.data.map { it[BudgetKeys.YEARLY_BUDGET] ?: 0.0 }

    suspend fun saveWeeklyBudget(value: Double) {
        context.budgetDataStore.edit { it[BudgetKeys.WEEKLY_BUDGET] = value }
    }

    suspend fun saveMonthlyBudget(value: Double) {
        context.budgetDataStore.edit { it[BudgetKeys.MONTHLY_BUDGET] = value }
    }

    suspend fun saveYearlyBudget(value: Double) {
        context.budgetDataStore.edit { it[BudgetKeys.YEARLY_BUDGET] = value }
    }
}
