package com.example.assessment2.database

import androidx.room.*
import com.example.assessment2.model.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: Transaction)

    @Query("SELECT * FROM transactions ORDER BY year DESC, month DESC, day DESC")
    fun getAllTransactions(): Flow<List<Transaction>>
}
