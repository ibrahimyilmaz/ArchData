package me.ibrahimyilmaz.arch_data

import androidx.lifecycle.MutableLiveData


inline fun <T> mutableLiveDataOf(): MutableLiveData<T> = MutableLiveData()

inline fun <T> publishLiveDataOf(): PublishLiveData<T> = PublishLiveData()

inline fun <T> replayLiveDataOf(): ReplayLiveData<T> = ReplayLiveData()



