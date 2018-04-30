package com.masterofcode.vocaburise.preferences

import com.masterofcode.vocaburise.api.bodies.SignUpData
import com.masterofcode.vocaburise.models.User
import io.reactivex.Single

/**
 * Created by andrews on 24.04.18.
 */
interface IUserPrefsManager {
    val accessToken: String?

    fun isLoggedIn(): Boolean
    fun signIn(email: String, password: String): Single<Boolean>
    fun signUp(data: SignUpData): Single<User>

}