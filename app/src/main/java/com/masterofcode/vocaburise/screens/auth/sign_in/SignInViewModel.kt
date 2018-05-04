package com.masterofcode.vocaburise.screens.auth.sign_in

import android.databinding.Bindable
import com.masterofcode.vocaburise.BR
import com.masterofcode.vocaburise.R
import com.masterofcode.vocaburise.base.BaseViewModel
import com.masterofcode.vocaburise.preferences.UserPrefsManager
import com.masterofcode.vocaburise.utils.async
import com.masterofcode.vocaburise.utils.strRes
import com.masterofcode.vocaburise.utils.toast
import com.masterofcode.vocaburise.utils.weak

/**
 * Created by andrews on 24.04.18.
 */
class SignInViewModel : BaseViewModel() {

    var interactor by weak<SignInInteractor>()

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

    fun init(interactor: SignInInteractor) {
        this.interactor = interactor
    }

    fun register() {
        interactor?.register()
    }

    fun signIn() {
        validateInputs()
        if (isDataValid()) {
            UserPrefsManager.signIn(email, password)
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

    fun forgotPassword() {

    }

    private fun showErrorMessage(throwable: Throwable) {
        val message = throwable.message ?: strRes(R.string.error_unknown)
        interactor?.showErrorSnackbar(message, throwable)
    }

    private fun isDataValid(): Boolean {
        return emailError.isEmpty() && passwordError.isEmpty()
    }

    private fun validateInputs() {
        validateLogin()
        validatePassword()
    }

    private fun validateLogin() {
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