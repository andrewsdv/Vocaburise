package com.masterofcode.vocaburise.screens.auth.sign_up

interface SignUpInteractor {

    fun finish()
    fun showErrorSnackbar(message: String, throwable: Throwable?)

}