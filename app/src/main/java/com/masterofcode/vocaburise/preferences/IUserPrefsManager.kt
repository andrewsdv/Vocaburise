package com.masterofcode.vocaburise.preferences

import com.masterofcode.vocaburise.api.bodies.SignUpData
import com.masterofcode.vocaburise.models.User
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.adapter.rxjava2.Result

/**
 * Created by andrews on 24.04.18.
 */
interface IUserPrefsManager {
    val accessToken: String?

    fun isLoggedIn(): Boolean
    fun signIn(email: String, password: String): Observable<Result<User>>
    fun signUp(data: SignUpData): Observable<Result<User>>
    fun signOut(): Completable
}