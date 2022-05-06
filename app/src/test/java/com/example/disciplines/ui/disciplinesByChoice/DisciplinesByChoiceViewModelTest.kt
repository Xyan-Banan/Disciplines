package com.example.disciplines.ui.disciplinesByChoice

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.disciplines.MainCoroutineRule
import com.example.disciplines.R
import com.example.disciplines.data.network.model.Discipline
import com.example.disciplines.data.network.model.DisciplinesBundle
import com.example.disciplines.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DisciplinesByChoiceViewModelTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule(TestCoroutineDispatcher())

    @get:Rule
    val ins = InstantTaskExecutorRule()

//    val dispatcher = TestCoroutineDispatcher()

    lateinit var viewModel: DisciplinesByChoiceViewModel

    @Before
    fun setUp() {
        viewModel = DisciplinesByChoiceViewModel()
    }
//
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain()
//        dispatcher.cleanupTestCoroutines()
//    }

    @Test
    fun getDisciplines() {
//        val viewModel = DisciplinesByChoiceViewModel()

        val list = viewModel.disciplinesList.getOrAwaitValue()

        val ex = listOf(
            DisciplinesBundle(
                listOf(
                    Discipline.ByChoice(intensity = 0),
                    Discipline.ByChoice(intensity = 0)
                )
            ),
            DisciplinesBundle(
                listOf(
                    Discipline.ByChoice(
                        intensity = 0
                    ),
                    Discipline.ByChoice(intensity = 0)
                )
            )
        )

        assertEquals(ex, list)
    }

    @Test
    fun getInstructions() {
        val list = viewModel.disciplinesList.getOrAwaitValue()
        val instructions = viewModel.instructions.getOrAwaitValue()

        if (list.isNullOrEmpty())
            assertEquals(instructions, R.string.instructions_disciplinesByChoice_empty)
        else
            assertEquals(instructions, R.string.instructions_disciplinesByChoice)
    }
}