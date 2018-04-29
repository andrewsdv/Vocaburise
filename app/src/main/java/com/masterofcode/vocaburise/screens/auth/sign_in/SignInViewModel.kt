package com.masterofcode.vocaburise.screens.auth.sign_in

import com.masterofcode.vocaburise.R
import com.masterofcode.vocaburise.base.BaseViewModel
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
            notifyPropertyChanged(BR.phoneNumber)
        }

    var loginError: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.phoneNumberError)
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
            CredentialsManager.signIn(phoneNumber.removePhoneCodeFormatting(), password)
                    .async()
                    .doOnSubscribe { progressBarVisible = true }
                    .doOnEvent { _, _ -> progressBarVisible = false }
                    .takeUntilCleared()
                    .subscribe({
                        if (!CredentialsManager.isActivated()) {
                            interactor?.activateAccount()
                        } else {
                            interactor?.finish(true)
                        }
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
        validatePhoneNumber()
        validatePassword()
    }

    private fun validatePhoneNumber() {
        val phoneNumberTrimmed = phoneNumber.trim()
        phoneNumber = phoneNumberTrimmed
        phoneNumberError = phoneNumber.validatePhone()
    }

    private fun validatePassword() {
        // Make only sure password is not blank
        passwordError = if (password.isBlank()) {
            strRes(R.string.signInScreenPasswordEmptyError)
        } else {
            ""
        }
    }

}