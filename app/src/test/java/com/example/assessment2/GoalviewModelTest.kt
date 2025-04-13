package com.example.assessment2

import com.example.assessment2.database.TransactionDao
import com.example.assessment2.model.Goal
import com.example.assessment2.repository.GoalRepository
import com.example.assessment2.viewmodel.GoalsViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class GoalViewModelTest {

    private lateinit var repository: GoalRepository
    private lateinit var transactionDao: TransactionDao
    private lateinit var viewModel: GoalsViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher) // 👈 设置 Main dispatcher

        repository = mock()
        transactionDao = mock()

        whenever(repository.getAllGoals()).thenReturn(flowOf(emptyList()))
        whenever(transactionDao.getAllTransactions()).thenReturn(flowOf(emptyList()))

        viewModel = GoalsViewModel(repository, transactionDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // 👈 重置为默认 dispatcher
    }

    @Test
    fun `test addGoal calls repository`() = runTest {
        viewModel.addGoal("Vacation", 1000.0, "2025-12-31")

        verify(repository).insertGoal(
            argThat { name == "Vacation" && amount == 1000.0 && deadline == "2025-12-31" }
        )
    }

    @Test
    fun `test deleteGoal calls repository`() = runTest {
        val goal = Goal(id = 1, name = "Vacation", amount = 1000.0, deadline = "2025-12-31", createdAt = "2024-04-11")
        viewModel.deleteGoal(goal)

        verify(repository).deleteGoal(goal)
    }
}
