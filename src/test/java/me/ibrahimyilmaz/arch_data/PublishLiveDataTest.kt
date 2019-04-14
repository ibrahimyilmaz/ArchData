package me.ibrahimyilmaz.arch_data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PublishLiveDataTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var liveData: PublishLiveData<Int>

    @Before
    fun setUp() {
        liveData = publishLiveDataOf()
    }

    @Test
    fun should_not_notify_when_there_is_no_event() {
        //GIVEN
        val observer = mock<Observer<Int>>()

        //WHEN
        liveData.observeForever(observer)

        //THEN
        verify(observer, never()).onChanged(any())
    }

    @Test
    fun should_not_notify_when_there_is_event_before_observation() {
        //GIVEN
        val observer = mock<Observer<Int>>()

        //WHEN
        liveData.postValue(5)
        liveData.observeForever(observer)

        //THEN
        verify(observer, never()).onChanged(any())
    }

    @Test
    fun should_notify_when_there_is_event() {
        //GIVEN
        val expectedValue = 5
        val observer = mock<Observer<Int>>()

        //WHEN
        liveData.observeForever(observer)
        liveData.postValue(expectedValue)

        //THEN
        verify(observer).onChanged(expectedValue)
    }

    @Test
    fun should_not_notify_new_observer_for_previous_events() {
        //GIVEN
        val observerOld = mock<Observer<Int>>()
        val observerNew = mock<Observer<Int>>()

        //WHEN
        liveData.observeForever(observerOld)

        liveData.postValue(1)
        liveData.postValue(2)
        liveData.postValue(3)

        liveData.observeForever(observerNew)

        //THEN
        verify(observerNew, never()).onChanged(1)
        verify(observerNew, never()).onChanged(2)
        verify(observerNew, never()).onChanged(3)
    }

    @Test
    fun should_notify_new_observers_for_only_new_events() {
        //GIVEN
        val observerOld = mock<Observer<Int>>()
        val observerNew = mock<Observer<Int>>()

        //WHEN
        liveData.observeForever(observerOld)
        liveData.postValue(1)
        liveData.postValue(2)
        liveData.postValue(3)

        liveData.observeForever(observerNew)
        liveData.postValue(4)
        liveData.postValue(5)

        //THEN
        verify(observerNew, never()).onChanged(1)
        verify(observerNew, never()).onChanged(2)
        verify(observerNew, never()).onChanged(3)
        val inOrderVerifier = inOrder(observerNew)
        inOrderVerifier.verify(observerNew).onChanged(4)
        inOrderVerifier.verify(observerNew).onChanged(5)
        inOrderVerifier.verifyNoMoreInteractions()
    }

    @Test
    fun should_receive_events_happening_after_subscription_for_re_subscription() {
        //GIVEN
        val observer = mock<Observer<Int>>()

        //WHEN
        liveData.observeForever(observer)

        liveData.postValue(1)
        liveData.postValue(2)


        liveData.removeObserver(observer)

        liveData.postValue(3)
        liveData.postValue(4)

        liveData.observeForever(observer)

        liveData.postValue(5)
        liveData.postValue(6)

        //THEN
        verify(observer, never()).onChanged(3)
        verify(observer, never()).onChanged(4)

        val inOrderVerifier = inOrder(observer)
        inOrderVerifier.verify(observer).onChanged(1)
        inOrderVerifier.verify(observer).onChanged(2)
        inOrderVerifier.verify(observer).onChanged(5)
        inOrderVerifier.verify(observer).onChanged(6)
        inOrderVerifier.verifyNoMoreInteractions()
    }

}