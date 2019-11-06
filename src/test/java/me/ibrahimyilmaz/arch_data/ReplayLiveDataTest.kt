package me.ibrahimyilmaz.arch_data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ReplayLiveDataTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var liveData: ReplayLiveData<Int>

    @Before
    fun setUp() {
        liveData = replayLiveDataOf()
    }

    @Test
    fun should_notify_new_observers_for_past_events() {
        //GIVEN
        val observerOld = mock<Observer<Int>>()
        val observerNew = mock<Observer<Int>>()

        //WHEN
        liveData.observeForever(observerOld)
        liveData.postValue(1)
        liveData.postValue(2)
        liveData.postValue(3)

        liveData.observeForever(observerNew)
        val inOrderVerifier = inOrder(observerNew)
        inOrderVerifier.verify(observerNew).onChanged(1)
        inOrderVerifier.verify(observerNew).onChanged(2)
        inOrderVerifier.verify(observerNew).onChanged(3)
        inOrderVerifier.verifyNoMoreInteractions()
    }

    @Test
    fun should_notify_observer_for_past_events_for_re_subscription() {
        //GIVEN
        val observer = mock<Observer<Int>>()

        //WHEN
        liveData.observeForever(observer)
        liveData.postValue(1)
        liveData.postValue(2)
        liveData.postValue(3)

        liveData.removeObserver(observer)
        liveData.observeForever(observer)

        val inOrderVerifier = inOrder(observer)
        inOrderVerifier.verify(observer).onChanged(1)
        inOrderVerifier.verify(observer).onChanged(2)
        inOrderVerifier.verify(observer).onChanged(3)
        inOrderVerifier.verify(observer).onChanged(1)
        inOrderVerifier.verify(observer).onChanged(2)
        inOrderVerifier.verify(observer).onChanged(3)
        inOrderVerifier.verifyNoMoreInteractions()
    }

    @Test
    fun should_receive_events_happened_when_observer_unsubscribed_for_re_subscription() {
        //GIVEN
        val observer = mock<Observer<Int>>()

        //WHEN
        liveData.observeForever(observer)
        liveData.postValue(1)
        liveData.postValue(2)
        liveData.postValue(3)

        liveData.removeObserver(observer)
        liveData.postValue(4)
        liveData.postValue(5)
        liveData.observeForever(observer)

        liveData.postValue(6)

        //THEN
        val inOrderVerifier = inOrder(observer)
        inOrderVerifier.verify(observer).onChanged(1)
        inOrderVerifier.verify(observer).onChanged(2)
        inOrderVerifier.verify(observer).onChanged(3)
        inOrderVerifier.verify(observer).onChanged(1)
        inOrderVerifier.verify(observer).onChanged(2)
        inOrderVerifier.verify(observer).onChanged(3)
        inOrderVerifier.verify(observer).onChanged(4)
        inOrderVerifier.verify(observer).onChanged(5)
        inOrderVerifier.verify(observer).onChanged(6)
        inOrderVerifier.verifyNoMoreInteractions()
    }

    @Test
    fun should_set_the_value_of_the_replay_livedata() {
        //GIVEN
        val value = 3

        //WHEN
        liveData.setValue(value)

        //THEN
        assertEquals(liveData.value, value)
    }

}