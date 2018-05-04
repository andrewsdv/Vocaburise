package com.masterofcode.vocaburise.api

import com.masterofcode.vocaburise.api.bodies.SignUpData
import com.masterofcode.vocaburise.models.User
import io.reactivex.Observable
import retrofit2.adapter.rxjava2.Result

/**
 * Created by andrews on 30.04.18.
 */
interface IApiRepository {

    fun signIn(email: String, password: String): Observable<Result<User>>
    fun signUp(data: SignUpData): Observable<Result<User>>

}