package me.ibrahimyilmaz.arch_data

import androidx.lifecycle.Observer


internal inline fun <T> singleEventObserverOf(observer: Observer<in T>): SingleLiveEventObserver<T> = SingleLiveEventObserver(observer)

internal class SingleLiveEventObserver<T>(private val observer: Observer<in T>) : Observer<SingleLiveDataEvent<T>> {
    override fun onChanged(t: SingleLiveDataEvent<T>?) {
        t?.getContentIfNotHandled(observer)?.let {
            observer.onChanged(it)
        }
    }
}

