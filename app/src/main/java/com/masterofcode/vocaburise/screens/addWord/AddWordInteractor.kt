package com.masterofcode.vocaburise.screens.addWord

interface AddWordInteractor {

    fun showErrorSnackbar(message: String, throwable: Throwable?)
    fun finish()

}