package com.example.assessment2.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat // ✅ 使用标准 Java 的格式化类
import java.util.Date
import java.util.Locale

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val year: Int,
    val month: Int,
    val day: Int,
    val type: String,   // "Income" 或 "Expense"
    val amount: Double,
    val reason: String,  // 来源 / 用途，不能为空（可以保存 "unknown"）
    val date: String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())  // ✅ 精确时间戳
)