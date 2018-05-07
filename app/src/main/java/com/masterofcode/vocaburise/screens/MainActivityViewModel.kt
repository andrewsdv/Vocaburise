package com.masterofcode.vocaburise.screens

import android.databinding.Bindable
import android.provider.UserDictionary
import com.masterofcode.vocaburise.BR
import com.masterofcode.vocaburise.R
import com.masterofcode.vocaburise.api.ApiRepository
import com.masterofcode.vocaburise.base.BaseViewModel
import com.masterofcode.vocaburise.models.Word
import com.masterofcode.vocaburise.utils.async
import com.masterofcode.vocaburise.utils.strRes
import com.masterofcode.vocaburise.utils.weak

class MainActivityViewModel : BaseViewModel() {

    var interactor by weak<MainActivityInteractor>()

    var words = emptyList<Word>().toString()
        @Bindable get

    var progressBarVisible: Boolean = false
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.progressBarVisible)
        }

    fun init(interactor: MainActivityInteractor) {
        this.interactor = interactor
        getWords()
    }

    private fun getWords() {
        ApiRepository.getWords()
                .async()
                .doOnSubscribe { progressBarVisible = true }
                .takeUntilCleared()
                .doOnEvent { _, _ -> progressBarVisible = false }
                .subscribe({
                    words = it.toString()
                }, {
                    showErrorMessage(it)
                })
    }

    private fun showErrorMessage(throwable: Throwable) {
        val message = throwable.message ?: strRes(R.string.errorUnknown)
        interactor?.showErrorSnackbar(message, throwable)
    }
}