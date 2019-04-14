package me.ibrahimyilmaz.arch_data

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class ReplayLiveData<T> : MutableLiveData<T>() {

    private val liveData = MemoryLessLiveData<T>()

    private val values = mutableListOf<T>()

    private val mutableDataEvent = mutableLiveDataOf<Observer<in T>>()

    private val eventObserver: (Observer<in T>) -> Unit = { observer ->
        values.forEach { observer.onChanged(it) }
    }

    init {
        mutableDataEvent.observeForever(eventObserver)
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        liveData.observe(owner, observer)
        mutableDataEvent.postValue(observer)
    }

    override fun observeForever(observer: Observer<in T>) {
        liveData.observeForever(observer)
        mutableDataEvent.postValue(observer)
    }

    override fun postValue(value: T) {
        values.add(value)
        liveData.postValue(value)
    }

    override fun setValue(value: T) {
        values.add(value)
        liveData.setValue(value)
    }

    override fun getValue(): T? {
        return liveData.value
    }

    protected fun finalize() {
        mutableDataEvent.removeObserver(eventObserver)
    }
}