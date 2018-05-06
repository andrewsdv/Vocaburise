package com.masterofcode.vocaburise.screens.auth.signUp

interface SignUpInteractor {

    fun openWordsScreen()
    fun showErrorSnackbar(message: String, throwable: Throwable?)

}