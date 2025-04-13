package com.example.assessment2.viewmodel

import com.example.assessment2.database.TransactionDao
import com.example.assessment2.model.Transaction
import com.example.assessment2.viewmodel.incomeexpense.IncomeExpenseViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class IncomeExpenseViewModelTest {

    private lateinit var transactionDao: TransactionDao
    private lateinit var viewModel: IncomeExpenseViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher) // ✅ 模拟主线程
        transactionDao = mock()
        viewModel = IncomeExpenseViewModel(transactionDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // ✅ 测试后恢复原样
    }

    @Test
    fun `test addTransaction inserts transaction into dao`() = runTest {
        viewModel.addTransaction("Income", 100.0, "Salary")

        // 因为 viewModelScope 默认用 Main dispatcher，需要手动执行协程任务
        testDispatcher.scheduler.advanceUntilIdle()

        val captor = argumentCaptor<Transaction>()
        verify(transactionDao).insert(captor.capture())

        val transaction = captor.firstValue
        assert(transaction.amount == 100.0)
        assert(transaction.type == "Income")
        assert(transaction.reason == "Salary")
        assert(transaction.year in 2000..Calendar.getInstance().get(Calendar.YEAR))
    }
}