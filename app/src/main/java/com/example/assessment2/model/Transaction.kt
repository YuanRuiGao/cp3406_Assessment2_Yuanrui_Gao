package com.example.assessment2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val year: Int,
    val month: Int,
    val day: Int,
    val type: String,   // "Income" 或 "Expense"
    val amount: Double,
    val reason: String  // 来源 / 用途，不能为空（可以保存 "unknown"）
)
