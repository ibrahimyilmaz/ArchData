package me.ibrahimyilmaz.arch_data

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer


internal fun <T> singleEventObserverOf(lifecycleOwner: LifecycleOwner?, observer: Observer<in T>): SingleLiveEventObserver<T> = SingleLiveEventObserver(lifecycleOwner, observer)

internal class SingleLiveEventObserver<T>(var lifecycleOwner: LifecycleOwner?, val observer: Observer<in T>) : Observer<SingleLiveDataEvent<T>> {
    override fun onChanged(t: SingleLiveDataEvent<T>?) {
        t?.getContentIfNotHandled(observer)?.let {
            observer.onChanged(it)
        }
    }
}

