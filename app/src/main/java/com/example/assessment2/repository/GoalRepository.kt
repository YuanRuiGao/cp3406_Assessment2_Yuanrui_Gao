package com.example.assessment2.repository

import com.example.assessment2.database.GoalDao
import com.example.assessment2.database.TransactionDao
import com.example.assessment2.model.Goal
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoalRepository @Inject constructor(
    private val goalDao: GoalDao,
    private val transactionDao: TransactionDao
) {
    fun getAllGoals(): Flow<List<Goal>> = goalDao.getAllGoals()

    fun getAllTransactions() = transactionDao.getAllTransactions()

    suspend fun insertGoal(goal: Goal) {
        goalDao.insertGoal(goal)
    }

    suspend fun deleteGoal(goal: Goal) {
        goalDao.deleteGoal(goal)
    }

    fun getCurrentTime(): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
    }
}
