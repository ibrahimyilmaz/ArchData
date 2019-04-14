package me.ibrahimyilmaz.arch_data

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

internal class TestingLifeCycleOwner(private val name: String) : LifecycleOwner {

    private val mLifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)

    init {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    override fun getLifecycle() = mLifecycleRegistry
}