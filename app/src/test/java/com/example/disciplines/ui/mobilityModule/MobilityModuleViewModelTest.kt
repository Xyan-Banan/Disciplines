package com.example.disciplines.ui.mobilityModule

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.disciplines.MainCoroutineRule
import com.example.disciplines.data.network.model.Discipline
import com.example.disciplines.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MobilityModuleViewModelTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule(TestCoroutineDispatcher())

    @get:Rule
    val ins = InstantTaskExecutorRule()

    lateinit var viewModel: MobilityModuleViewModel

    @Before
    fun setUp() {
        viewModel = MobilityModuleViewModel()
    }

    @Test
    fun getModules() {

        val list = viewModel.modulesList.getOrAwaitValue()

        val ex = listOf(
            Discipline.MobilityModule(
                name = "Атомная энергетика. Введение",
                platform = "НПОО",
                intensity = 3,
                hours = 108,
                link = "https://openedu.ru/course/spbstu/NUCPOW/"
            ),
            Discipline.MobilityModule(
                name = "Биомеханика",
                platform = "НПОО",
                intensity = 3,
                hours = 108,
                link = "https://openedu.ru/course/spbstu/BIOMECH/"
            ),
            Discipline.MobilityModule(
                name = "Бухгалтерский учет",
                platform = "СДО",
                intensity = 5,
                hours = 180,
                link = "https://dl-imet.spbstu.ru/course/view.php?id=7295"
            ),
            Discipline.MobilityModule
                (
                name = "Высшая математика (для технических направлений, 1 семестр)",
                platform = "СДО",
                intensity = 4,
                hours = 144,
                link = "https://lms.spbstu.ru/course/view.php?id=12948"
            )
        )

        assertEquals(ex, list)
    }

}