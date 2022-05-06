package com.example.disciplines.ui.electives

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.disciplines.R
import com.example.disciplines.ui.confirmation.ConfirmationViewModel
import com.example.disciplines.ui.confirmation.ConfirmationViewModelFactory
import com.example.disciplines.ui.mobilityModule.MobilityModuleViewModel
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ElectivesViewModelFactoryTest{
    @Test
    fun create_onOtherViewModel_throwsException() {
        val factory = ElectivesViewModelFactory(ApplicationProvider.getApplicationContext(), {})

        Assert.assertThrows(IllegalArgumentException::class.java) {
            factory.create(
                MobilityModuleViewModel::class.java
            )
        }
    }

    @Test
    fun create_returnsProperViewModel() {
        val factory = ElectivesViewModelFactory(ApplicationProvider.getApplicationContext(), {})

        val vm = factory.create(ElectivesViewModel::class.java)

        assert(vm.javaClass == ElectivesViewModel::class.java)
    }
}