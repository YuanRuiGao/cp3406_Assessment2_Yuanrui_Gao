package com.example.assessment2.database

import androidx.room.* // 确保导入了Room相关的注解
import com.example.assessment2.model.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) // 正确导入Insert和OnConflictStrategy
    suspend fun insert(transaction: Transaction) // 插入单条记录

    @Query("SELECT * FROM transactions ORDER BY year DESC, month DESC, day DESC")
    fun getAllTransactions(): Flow<List<Transaction>>
}
