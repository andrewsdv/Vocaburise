package com.masterofcode.vocaburise.screens.auth.sign_up

import android.databinding.Bindable
import com.masterofcode.vocaburise.BR
import com.masterofcode.vocaburise.R
import com.masterofcode.vocaburise.api.bodies.SignUpData
import com.masterofcode.vocaburise.base.BaseViewModel
import com.masterofcode.vocaburise.preferences.UserPrefsManager
import com.masterofcode.vocaburise.utils.async
import com.masterofcode.vocaburise.utils.strRes
import com.masterofcode.vocaburise.utils.toast
import com.masterofcode.vocaburise.utils.weak

class SignUpViewModel : BaseViewModel() {

    var interactor by weak<SignUpInteractor>()

    var name: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }

    var nameError: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.nameError)
        }

    var email: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.email)
        }

    var emailError: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.emailError)
        }

    var password: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.password)
        }

    var passwordError: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.passwordError)
        }

    var progressBarVisible: Boolean = false
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.progressBarVisible)
        }

    fun init(interactor: SignUpInteractor) {
        this.interactor = interactor
    }

    fun register() {
        validateInputs()
        if (isDataValid()) {
            UserPrefsManager.signUp(SignUpData(name, email, password))
                    .async()
                    .doOnSubscribe { progressBarVisible = true }
                    .takeUntilCleared()
                    .subscribe({
                        progressBarVisible = false
                        toast(strRes(R.string.done))
                        interactor?.openWordsScreen()
                    }, {
                        progressBarVisible = false
                        showErrorMessage(it)
                    }
                    )
        }
    }

    private fun showErrorMessage(throwable: Throwable) {
        val message = throwable.message ?: strRes(R.string.error_unknown)
        interactor?.showErrorSnackbar(message, throwable)
    }

    private fun isDataValid(): Boolean {
        return nameError.isEmpty() && emailError.isEmpty() && passwordError.isEmpty()
    }

    private fun validateInputs() {
        validateName()
        validateEmail()
        validatePassword()
    }

    private fun validateName() {
        val loginTrimmed = name.trim()
        name = loginTrimmed
        nameError = if (name.isBlank()) {
            strRes(R.string.signUpScreenNameEmptyError)
        } else ""
    }

    private fun validateEmail() {
        val loginTrimmed = email.trim()
        email = loginTrimmed
        emailError = if (email.isBlank()) {
            strRes(R.string.signInScreenEmailEmptyError)
        } else ""
    }

    private fun validatePassword() {
        // Make only sure password is not blank
        passwordError = if (password.isBlank()) {
            strRes(R.string.signInScreenPasswordEmptyError)
        } else ""
    }

}