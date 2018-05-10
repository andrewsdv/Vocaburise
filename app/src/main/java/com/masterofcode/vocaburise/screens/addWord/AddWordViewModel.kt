package com.masterofcode.vocaburise.screens.addWord

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

class AddWordViewModel : BaseViewModel() {

    var interactor by weak<AddWordInteractor>()

    var word: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.word)
        }

    var wordError: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.wordError)
        }

    var translation: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.translation)
        }

    var translationError: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.translationError)
        }

    var progressBarVisible: Boolean = false
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.progressBarVisible)
        }

    private fun isDataValid(): Boolean {
        return wordError.isEmpty() && translationError.isEmpty()
    }

    private fun validateInputs() {
        validateWord()
        validateTranslation()
    }

    private fun validateWord() {
        val loginTrimmed = word.trim()
        word = loginTrimmed
        wordError = if (word.isBlank()) {
            strRes(R.string.addWordScreenWordEmptyError)
        } else ""
    }

    private fun validateTranslation() {
        translationError = if (translation.isBlank()) {
            strRes(R.string.addWordScreenTranslationEmptyError)
        } else ""
    }

    fun save() {
        validateInputs()
        if (isDataValid()) {
            ApiRepository.addWord(Word(word = word, translation = translation))
                    .async()
                    .doOnSubscribe { progressBarVisible = true }
                    .takeUntilCleared()
                    .subscribe({
                        progressBarVisible = false
                        clearInputs()
                        interactor?.finish()
                    }, {
                        progressBarVisible = false
                        showErrorMessage(it)
                    }
                    )
        }
    }

    private fun clearInputs() {
        word = ""
        translation = ""
    }

    private fun showErrorMessage(throwable: Throwable) {
        val message = throwable.message ?: strRes(R.string.errorUnknown)
        interactor?.showErrorSnackbar(message, throwable)
    }
}