package com.example.assessment2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assessment2.model.Goal
import com.example.assessment2.repository.GoalRepository
import com.example.assessment2.database.TransactionDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class GoalsViewModel @Inject constructor(
    private val goalRepository: GoalRepository,
    private val transactionDao: TransactionDao
) : ViewModel() {

    val allGoals: StateFlow<List<Goal>> = goalRepository.getAllGoals()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allTransactions = transactionDao.getAllTransactions()

    fun addGoal(name: String, amount: Double, deadline: String) {
        val now = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        viewModelScope.launch {
            goalRepository.insertGoal(Goal(name = name, amount = amount, deadline = deadline, createdAt = now))
        }
    }

    fun deleteGoal(goal: Goal) {
        viewModelScope.launch {
            goalRepository.deleteGoal(goal)
        }
    }
}
