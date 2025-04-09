package com.example.assessment2.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assessment2.database.TransactionDao
import com.example.assessment2.model.Transaction
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class IncomeExpenseViewModel(private val dao: TransactionDao) : ViewModel() {

    fun addTransaction(type: String, amount: Double) {
        val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        val transaction = Transaction(type = type, amount = amount, date = date)

        viewModelScope.launch {
            dao.insert(transaction)
        }
    }
}