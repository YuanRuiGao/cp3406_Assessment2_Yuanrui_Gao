package com.example.assessment2.viewmodel.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assessment2.database.TransactionDao
import com.example.assessment2.datastore.BudgetDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val dao: TransactionDao,
    private val budgetStore: BudgetDataStore
) : ViewModel() {

    val allTransactions = dao.getAllTransactions()

    val weeklyBudget: Flow<Double> = budgetStore.weeklyBudget
    val monthlyBudget: Flow<Double> = budgetStore.monthlyBudget
    val yearlyBudget: Flow<Double> = budgetStore.yearlyBudget

    fun saveBudget(week: Double?, month: Double?, year: Double?) {
        viewModelScope.launch {
            week?.let { budgetStore.saveWeeklyBudget(it) }
            month?.let { budgetStore.saveMonthlyBudget(it) }
            year?.let { budgetStore.saveYearlyBudget(it) }
        }
    }
}
