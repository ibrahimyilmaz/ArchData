package me.ibrahimyilmaz.arch_data

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer


fun <T> mutableLiveDataOf(): MutableLiveData<T> = MutableLiveData()

fun <T> publishLiveDataOf(): PublishLiveData<T> = PublishLiveData()

fun <T> replayLiveDataOf(): ReplayLiveData<T> = ReplayLiveData()

fun <T> LiveData<T>.nonNullObserve(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner, Observer {
        it?.let(observer)
    })
}
