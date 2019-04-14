package me.ibrahimyilmaz.arch_data

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class PublishLiveData<T> : MutableLiveData<T>() {

    private val liveData = MemoryLessLiveData<T>()

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        liveData.observe(owner, observer)
    }

    override fun observeForever(observer: Observer<in T>) {
        liveData.observeForever(observer)
    }

    override fun removeObserver(observer: Observer<in T>) {
        liveData.removeObserver(observer)
    }

    override fun removeObservers(owner: LifecycleOwner) {
        liveData.removeObservers(owner)
    }

    override fun postValue(value: T) {
        liveData.postValue(value)
    }

    override fun setValue(value: T) {
        liveData.setValue(value)
    }

    override fun getValue(): T? {
        return liveData.value
    }
}