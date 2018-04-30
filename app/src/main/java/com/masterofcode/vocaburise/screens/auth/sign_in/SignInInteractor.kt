package com.masterofcode.vocaburise.screens.auth.sign_in

/**
 * Created by andrews on 24.04.18.
 */
interface SignInInteractor {

    fun register()
    fun finish()
    fun showErrorSnackbar(message: String, throwable: Throwable?)

}