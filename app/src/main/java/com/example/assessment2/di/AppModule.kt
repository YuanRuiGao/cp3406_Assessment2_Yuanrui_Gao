package com.example.assessment2.di

import android.content.Context
import androidx.room.Room
import com.example.assessment2.api.ExchangeRateApi
import com.example.assessment2.database.FinanceDatabase
import com.example.assessment2.database.GoalDao
import com.example.assessment2.database.ReminderDao
import com.example.assessment2.database.TransactionDao
import com.example.assessment2.datastore.SettingsDataStore
import com.example.assessment2.repository.GoalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFinanceDatabase(@ApplicationContext context: Context): FinanceDatabase {
        return Room.databaseBuilder(
            context,
            FinanceDatabase::class.java,
            "finance_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideGoalDao(db: FinanceDatabase): GoalDao = db.goalDao()

    @Provides
    fun provideTransactionDao(db: FinanceDatabase): TransactionDao = db.transactionDao()

    @Provides
    fun provideReminderDao(db: FinanceDatabase): ReminderDao = db.reminderDao()

    @Provides
    fun provideSettingsDataStore(@ApplicationContext context: Context): SettingsDataStore {
        return SettingsDataStore(context)
    }

    @Provides
    fun provideGoalRepository(
        goalDao: GoalDao,
        transactionDao: TransactionDao
    ): GoalRepository {
        return GoalRepository(goalDao, transactionDao)
    }
    @Provides
    @Singleton
    fun provideExchangeRateApi(): ExchangeRateApi {
        return Retrofit.Builder()
            .baseUrl("https://api.exchangerate.host/")  // 你的汇率 API 地址
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExchangeRateApi::class.java)
    }
}


