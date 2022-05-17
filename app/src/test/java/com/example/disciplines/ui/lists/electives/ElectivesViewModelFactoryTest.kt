package com.example.disciplines.ui.lists.electives

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.disciplines.GroupNumberInfo
import com.example.disciplines.ui.lists.mobilityModule.MobilityModuleViewModel
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ElectivesViewModelFactoryTest{
    @Test
    fun create_onOtherViewModel_throwsException() {
        val factory = ElectivesViewModelFactory(ApplicationProvider.getApplicationContext(),
            GroupNumberInfo("")
        )

        Assert.assertThrows(IllegalArgumentException::class.java) {
            factory.create(
                MobilityModuleViewModel::class.java
            )
        }
    }

    @Test
    fun create_returnsProperViewModel() {
        val factory = ElectivesViewModelFactory(ApplicationProvider.getApplicationContext(),GroupNumberInfo(""))

        val vm = factory.create(ElectivesViewModel::class.java)

        assert(vm.javaClass == ElectivesViewModel::class.java)
    }
}