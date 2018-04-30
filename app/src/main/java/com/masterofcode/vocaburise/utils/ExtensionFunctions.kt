package com.masterofcode.vocaburise.utils

import android.databinding.BindingAdapter
import android.support.annotation.StringRes
import android.support.design.widget.TextInputLayout
import com.masterofcode.vocaburise.App
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by andrews on 30.04.18.
 */


fun Any.strRes(@StringRes resId: Int, vararg formatArgs: Any): String {
    return App.app.getString(resId, *formatArgs)
}

// Rx Java extensions
fun <T> Single<T>.subscribeOnIoThread(): Single<T> = subscribeOn(Schedulers.io())
fun <T> Single<T>.observeOnMainThread(): Single<T> = observeOn(AndroidSchedulers.mainThread())
fun <T> Single<T>.async(): Single<T> = subscribeOnIoThread().observeOnMainThread()

// Binding extensions
@BindingAdapter("errorText")
fun setErrorText(view: TextInputLayout, errorText: String?) {
    view.isErrorEnabled = errorText?.isEmpty() ?: false
    view.error = errorText
}