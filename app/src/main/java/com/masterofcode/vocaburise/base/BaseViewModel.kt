package com.masterofcode.vocaburise.base

import android.arch.lifecycle.ViewModel
import android.databinding.Bindable
import android.databinding.Observable
import android.databinding.PropertyChangeRegistry
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.processors.PublishProcessor
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.CancellationException

/**
 * Created by andrews on 24.04.18.
 */
open class BaseViewModel : ViewModel(), android.databinding.Observable {
    @Transient
    private var callbacks: PropertyChangeRegistry? = null

    private val clearedProcessor = PublishProcessor.create<Unit>()
    private val clearedSubject = PublishSubject.create<Unit>()

    override fun onCleared() {
        super.onCleared()
        clearedProcessor.onNext(Unit)
        clearedSubject.onNext(Unit)
    }

    fun <T> Flowable<T>.takeUntilCleared(): Flowable<T> = this.takeUntil(clearedProcessor)
            .onErrorResumeNext { t: Throwable -> if (t is CancellationException) Flowable.empty() else Flowable.error(t) }

    fun <T> Maybe<T>.takeUntilCleared(): Maybe<T> = this.takeUntil(clearedProcessor)
            .onErrorResumeNext { t: Throwable -> if (t is CancellationException) Maybe.empty() else Maybe.error(t) }

    fun <T> Single<T>.takeUntilCleared(): Maybe<T> = this.toMaybe().takeUntil(clearedProcessor)
            .onErrorResumeNext { t: Throwable -> if (t is CancellationException) Maybe.empty() else Maybe.error(t) }

    fun <T> io.reactivex.Observable<T>.takeUntilCleared(): io.reactivex.Observable<T> = this.takeUntil(clearedSubject)
            .onErrorResumeNext { t: Throwable -> if (t is CancellationException) io.reactivex.Observable.empty() else io.reactivex.Observable.error(t) }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) {
            if (callbacks == null) {
                callbacks = PropertyChangeRegistry()
            }
        }
        callbacks!!.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) {
            if (callbacks == null) {
                return
            }
        }
        callbacks!!.remove(callback)
    }

    /**
     * Notifies listeners that all properties of this instance have changed.
     */
    fun notifyChange() {
        synchronized(this) {
            if (callbacks == null) {
                return
            }
        }
        callbacks!!.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies listeners that a specific property has changed. The getter for the property
     * that changes should be marked with [Bindable] to generate a field in
     * `BR` to be used as `fieldId`.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    fun notifyPropertyChanged(fieldId: Int) {
        synchronized(this) {
            if (callbacks == null) {
                return
            }
        }
        callbacks!!.notifyCallbacks(this, fieldId, null)
    }

}