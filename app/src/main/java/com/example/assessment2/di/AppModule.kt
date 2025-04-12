package com.example.assessment2.di

import android.content.Context
import com.example.assessment2.database.FinanceDatabase
import com.example.assessment2.database.GoalDao
import com.example.assessment2.database.ReminderDao
import com.example.assessment2.database.TransactionDao
import com.example.assessment2.repository.GoalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton
import kotlin.text.Typography.dagger

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFinanceDatabase(@ApplicationContext context: Context): FinanceDatabase {
        return FinanceDatabase.getDatabase(context)
    }

    @Provides
    fun provideGoalDao(db: FinanceDatabase): GoalDao = db.goalDao()

    @Provides
    fun provideTransactionDao(db: FinanceDatabase): TransactionDao = db.transactionDao()
    @Provides
    fun provideReminderDao(db: FinanceDatabase): ReminderDao = db.reminderDao()
    @Provides
    fun provideGoalRepository(
        goalDao: GoalDao,
        transactionDao: TransactionDao
    ): GoalRepository {
        return GoalRepository(goalDao, transactionDao)
    }
}
