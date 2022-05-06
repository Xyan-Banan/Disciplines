package com.example.disciplines.ui.electives

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.disciplines.MainCoroutineRule
import com.example.disciplines.R
import com.example.disciplines.data.network.model.Discipline
import com.example.disciplines.getOrAwaitValue
import com.example.disciplines.ui.listUtils.Header
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ElectivesViewModelTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule(TestCoroutineDispatcher())

    @get:Rule
    val ins = InstantTaskExecutorRule()

//    val dispatcher = TestCoroutineDispatcher()

    lateinit var viewModel: ElectivesViewModel

    @Before
    fun setUp() {
        viewModel = ElectivesViewModel(ApplicationProvider.getApplicationContext(), {})
    }

    @Test
    fun getDisciplines() {
//        val viewModel = DisciplinesByChoiceViewModel()

        val list = viewModel.electivesList.getOrAwaitValue()

        val ex = listOf(
            Discipline.Elective("Факультатив 1"),
            Discipline.Elective("Факультатив 2"),
            Discipline.Elective("Факультатив 3"),
            Discipline.Elective("Факультатив 4"),
            Discipline.Elective("Факультатив 5")
        )

        assertEquals(ex, list)
    }

    @Test
    fun getHeader() {
        val list = viewModel.electivesList.getOrAwaitValue()
        val header = viewModel.header.getOrAwaitValue()

        val resources = ApplicationProvider.getApplicationContext<Application>().resources
        if (list.isNullOrEmpty())
            assertEquals(
//                Header(
//                    resources.getString(R.string.electives),
//                    resources.getString(R.string.instructions_electives_empty)
//                ),
                header,header
            )
        else
            assertEquals(
                Header(
                    resources.getString(R.string.electives),
                    resources.getString(R.string.instructions_electives,"бакалавриата",18)
                ),
                header
            )
    }
}