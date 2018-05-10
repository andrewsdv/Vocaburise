package com.masterofcode.vocaburise.screens

import android.databinding.Bindable
import com.masterofcode.vocaburise.BR
import com.masterofcode.vocaburise.R
import com.masterofcode.vocaburise.api.ApiRepository
import com.masterofcode.vocaburise.base.BaseViewModel
import com.masterofcode.vocaburise.models.Word
import com.masterofcode.vocaburise.utils.async
import com.masterofcode.vocaburise.utils.strRes
import com.masterofcode.vocaburise.utils.toast
import com.masterofcode.vocaburise.utils.weak


class MainActivityViewModel : BaseViewModel() {

    var interactor by weak<MainActivityInteractor>()

    var welcomeMessage = strRes(R.string.mainScreenWelcomeMessage) // TODO: 10.05.18 include username
        @Bindable get

    var wordsNumber = strRes(R.string.mainScreenWordsNumber, strRes(R.string.no))
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.wordsNumber)
        }

    var words = emptyList<Word>()

    var progressBarVisible: Boolean = false
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.progressBarVisible)
        }

    fun init(interactor: MainActivityInteractor) {
        this.interactor = interactor
        fetchWords()
    }

    fun fetchWords() {
        ApiRepository.getWords()
                .async()
                .doOnSubscribe { progressBarVisible = true }
                .takeUntilCleared()
                .subscribe({
                    progressBarVisible = false
                    words = it.data
                    wordsNumber = strRes(R.string.mainScreenWordsNumber, it.data.size)
                }, {
                    progressBarVisible = false
                    showErrorMessage(it)
                })
    }

    private fun showErrorMessage(throwable: Throwable) {
        val message = throwable.message ?: strRes(R.string.errorUnknown)
        interactor?.showErrorSnackbar(message, throwable)
    }

    fun startLearning() {
        toast("TODO")
    }
}