package com.example.myapplicationtest.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.myapplicationtest.entity.BusEvent
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject


object RxBus {
    private val eventLiveData = MutableLiveData<BusEvent>()

    private val bus: Subject<Any> = PublishSubject.create<Any?>().toSerialized()

    fun post(event: Any) {
        bus.onNext(event)
    }

    fun <T : Any> toObservable(eventType: Class<T>): Observable<T> {
        return bus.ofType(eventType)
    }
}