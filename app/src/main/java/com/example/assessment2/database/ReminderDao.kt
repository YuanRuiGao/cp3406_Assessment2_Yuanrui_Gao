package com.example.assessment2.database

import androidx.room.*
import com.example.assessment2.model.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reminder: Reminder)

    @Delete
    suspend fun delete(reminder: Reminder)

    @Query("SELECT * FROM reminders ORDER BY date ASC")
    fun getAllReminders(): Flow<List<Reminder>>
}
