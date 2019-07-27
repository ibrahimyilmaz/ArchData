package me.ibrahimyilmaz.arch_data

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

object LiveDataAction

fun <T> mutableLiveDataOf(): MutableLiveData<T> = MutableLiveData()

fun <T> publishLiveDataOf(): PublishLiveData<T> = PublishLiveData()

fun <T> replayLiveDataOf(): ReplayLiveData<T> = ReplayLiveData()

fun <T> LiveData<T>.nonNullObserve(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner, Observer {
        it?.let(observer)
    })
}

fun LiveData<LiveDataAction>.observe(owner: LifecycleOwner, observer: () -> Unit) {
    this.observe(owner, Observer {
        observer()
    })
}

fun LiveData<LiveDataAction>.nonNullObserve(owner: LifecycleOwner, observer: () -> Unit) {
    this.observe(owner, Observer {
        it?.let { observer() }
    })
}


fun MutableLiveData<LiveDataAction>.postValue() {
    this.postValue(LiveDataAction)
}

@MainThread
fun MutableLiveData<LiveDataAction>.sendValue() {
    this.value = LiveDataAction
}