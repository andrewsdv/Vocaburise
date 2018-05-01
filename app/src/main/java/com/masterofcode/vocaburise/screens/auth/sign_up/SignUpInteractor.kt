package com.masterofcode.vocaburise.screens.auth.sign_up

interface SignUpInteractor {

    fun openWordsScreen()
    fun showErrorSnackbar(message: String, throwable: Throwable?)

}