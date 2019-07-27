package me.ibrahimyilmaz.arch_data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LiveDataExtensionsTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val lifecycleOwner = TestingLifeCycleOwner("lifecycleOwner")

    @Test
    fun `nonNullObserve should fire method with non null parameter when it is fired with non null data `() {
        //GIVEN
        val testData = "Test"
        val liveData = MutableLiveData<String>()
        val nonNullObserver = mock<(t: String) -> Unit>()
        liveData.nonNullObserve(lifecycleOwner, nonNullObserver)

        //WHEN
        liveData.value = testData

        //THEN
        verify(nonNullObserver).invoke(testData)
    }

    @Test
    fun `nonNullObserve should not fire method with non null parameter when it is fired with  null data `() {
        //GIVEN
        val liveData = MutableLiveData<String>()
        val nonNullObserver = mock<(t: String) -> Unit>()
        liveData.nonNullObserve(lifecycleOwner, nonNullObserver)

        //WHEN
        liveData.value = null

        //THEN
        verify(nonNullObserver, times(0)).invoke(any())
    }

    @Test
    fun `LiveData with LiveDataAction should fire observer without parameter`() {
        //GIVEN
        val liveData = MutableLiveData<LiveDataAction>()
        val observerWithoutParameter = mock<() -> Unit>()
        liveData.observe(lifecycleOwner, observerWithoutParameter)

        //WHEN
        liveData.value = null

        //THEN
        verify(observerWithoutParameter).invoke()
    }

    @Test
    fun `LiveData with LiveDataAction should fire observer with nonNull value when it is observed by nonNullObserver`() {
        //GIVEN
        val liveData = MutableLiveData<LiveDataAction>()
        val observerWithoutParameter = mock<() -> Unit>()
        liveData.nonNullObserve(lifecycleOwner, observerWithoutParameter)

        //WHEN
        liveData.postValue()

        //THEN
        verify(observerWithoutParameter).invoke()
    }

    @Test
    fun `LiveData with LiveDataAction should not fire observer with null value when it is observed by nonNullObserver`() {
        //GIVEN
        val liveData = MutableLiveData<LiveDataAction>()
        val observerWithoutParameter = mock<() -> Unit>()
        liveData.nonNullObserve(lifecycleOwner, observerWithoutParameter)

        //WHEN
        liveData.postValue(null)

        //THEN
        verify(observerWithoutParameter, times(0)).invoke()
    }
}