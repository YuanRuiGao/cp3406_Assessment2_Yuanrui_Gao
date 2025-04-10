package com.example.assessment2.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assessment2.database.TransactionDao
import com.example.assessment2.model.Transaction
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class IncomeExpenseViewModel(private val dao: TransactionDao) : ViewModel() {

    fun addTransaction(type: String, amount: Double, reason: String = "unknown") {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val transaction = Transaction(
            year = year,
            month = month,
            day = day,
            type = type,
            amount = amount,
            reason = reason
        )

        viewModelScope.launch {
            dao.insert(transaction)
        }
    }
}