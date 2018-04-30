package com.masterofcode.vocaburise.screens.auth.sign_in

import android.databinding.Bindable
import com.masterofcode.vocaburise.BR
import com.masterofcode.vocaburise.R
import com.masterofcode.vocaburise.base.BaseViewModel
import com.masterofcode.vocaburise.preferences.UserPrefsManager
import com.masterofcode.vocaburise.utils.async
import com.masterofcode.vocaburise.utils.strRes
import com.masterofcode.vocaburise.utils.weak

/**
 * Created by andrews on 24.04.18.
 */
class SignInViewModel : BaseViewModel() {

    var interactor by weak<SignInInteractor>()

    var login: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.login)
        }

    var loginError: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.loginError)
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
            UserPrefsManager.signIn(login, password)
                    .async()
                    .doOnSubscribe { progressBarVisible = true }
                    .doOnEvent { _, _ -> progressBarVisible = false }
                    .takeUntilCleared()
                    .subscribe({
                        interactor?.finish()
                    },
                            this::showErrorMessage
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
        return loginError.isEmpty() && passwordError.isEmpty()
    }

    private fun validateInputs() {
        validateLogin()
        validatePassword()
    }

    private fun validateLogin() {
        val loginTrimmed = login.trim()
        login = loginTrimmed
        loginError = if (login.isBlank()) {
            strRes(R.string.signInScreenLoginEmptyError)
        } else ""
    }

    private fun validatePassword() {
        // Make only sure password is not blank
        passwordError = if (password.isBlank()) {
            strRes(R.string.signInScreenPasswordEmptyError)
        } else ""
    }

}