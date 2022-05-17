package com.example.disciplines.ui.confirmation

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.disciplines.R
import com.example.disciplines.ui.lists.mobilityModule.MobilityModuleViewModel

import org.junit.Assert.assertThrows
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConfirmationViewModelFactoryTest {
    @Test
    fun create_onOtherViewModel_throwsException() {
        val factory = ConfirmationViewModelFactory(
            arrayOf(),
            R.id.mobilityModuleFragment,
            ApplicationProvider.getApplicationContext()
        )

        assertThrows(IllegalArgumentException::class.java) { factory.create(MobilityModuleViewModel::class.java) }
    }

    @Test
    fun create_returnsProperViewModel() {
        val factory = ConfirmationViewModelFactory(
            arrayOf(),
            R.id.mobilityModuleFragment,
            ApplicationProvider.getApplicationContext()
        )

        val vm = factory.create(ConfirmationViewModel::class.java)

        assert(vm.javaClass == ConfirmationViewModel::class.java)
    }
}