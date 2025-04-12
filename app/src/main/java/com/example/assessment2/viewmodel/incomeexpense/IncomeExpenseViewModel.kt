package com.example.assessment2.viewmodel.incomeexpense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assessment2.database.TransactionDao
import com.example.assessment2.model.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class IncomeExpenseViewModel @Inject constructor(
    private val dao: TransactionDao
) : ViewModel() {

    fun addTransaction(type: String, amount: Double, reason: String = "unknown") {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val date = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

        val transaction = Transaction(
            year = year,
            month = month,
            day = day,
            type = type,
            amount = amount,
            reason = reason,
            date = date
        )

        viewModelScope.launch {
            dao.insert(transaction)
        }
    }
}