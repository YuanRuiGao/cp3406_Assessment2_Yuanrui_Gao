package com.example.assessment2

import com.example.assessment2.database.ReminderDao
import com.example.assessment2.model.Reminder
import com.example.assessment2.viewmodel.reminder.ReminderViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class ReminderViewModelTest {

    private lateinit var dao: ReminderDao
    private lateinit var viewModel: ReminderViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        dao = mock()
        viewModel = ReminderViewModel(dao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test insertReminder calls dao`() = runTest {
        val reminder = Reminder(id = 1, name = "Electric Bill", date = "2025-04-20", frequency = "Monthly")
        viewModel.insertReminder(reminder)
        verify(dao).insert(reminder)
    }

    @Test
    fun `test deleteReminder calls dao`() = runTest {
        val reminder = Reminder(id = 2, name = "Water Bill", date = "2025-04-30", frequency = "Weekly")
        viewModel.deleteReminder(reminder)
        verify(dao).delete(reminder)
    }
}
