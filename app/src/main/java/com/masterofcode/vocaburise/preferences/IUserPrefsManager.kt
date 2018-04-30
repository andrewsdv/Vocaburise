package com.masterofcode.vocaburise.preferences

import io.reactivex.Single

/**
 * Created by andrews on 24.04.18.
 */
interface IUserPrefsManager {

    fun isLoggedIn(): Boolean
    fun signIn(login: String, password: String): Single<Unit>

}