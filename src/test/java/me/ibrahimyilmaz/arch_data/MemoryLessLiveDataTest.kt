package me.ibrahimyilmaz.arch_data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MemoryLessLiveDataTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var liveData: MemoryLessLiveData<Int>

    @Before
    fun setUp() {
        liveData = MemoryLessLiveData()
    }

    @Test
    fun should_remove_only_observers_of_specific_life_cycle_owner_when_removing_observers() {
        //GIVEN
        val observer = mock<Observer<Int>>()
        val observer2 = mock<Observer<Int>>()
        val lifecycleOwner = TestingLifeCycleOwner("lifecycleOwner")
        val lifecycleOwner2 = TestingLifeCycleOwner("lifecycleOwner2")

        //WHEN
        liveData.observe(lifecycleOwner, observer)
        liveData.observe(lifecycleOwner2, observer2)
        liveData.removeObservers(lifecycleOwner)

        //THEN
        val containsAnyObserverForLifeCycleOwner = liveData.observerMap.values.any { it.lifecycleOwner == lifecycleOwner }
        val containsAnyObserverForLifeCycleOwner2 = liveData.observerMap.values.any { it.lifecycleOwner == lifecycleOwner2 }
        assertEquals(false, containsAnyObserverForLifeCycleOwner)
        assertEquals(true, containsAnyObserverForLifeCycleOwner2)
    }

    @Test
    fun should_set_the_value_of_the_memoryless_livedata() {
        // GIVEN
        val value = 3

        // WHEN
        liveData.setValue(value)

        // THEN
        assertEquals(liveData.value, value)
    }
}