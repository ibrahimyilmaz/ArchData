package me.ibrahimyilmaz.arch_data

import androidx.lifecycle.Observer


internal class SingleLiveDataEvent<T>(observers: MutableCollection<Observer<in T>>, private val content: T) {

    private val receiverObservers = ArrayList(observers)

    fun getContentIfNotHandled(observer: Observer<in T>): T? =
            receiverObservers.firstOrNull { it == observer }?.let {
                receiverObservers.remove(it)
                content
            }
}


