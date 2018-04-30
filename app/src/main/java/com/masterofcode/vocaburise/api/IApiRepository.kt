package com.masterofcode.vocaburise.api

import com.masterofcode.vocaburise.api.bodies.SignUpData
import com.masterofcode.vocaburise.models.User
import io.reactivex.Single

/**
 * Created by andrews on 30.04.18.
 */
interface IApiRepository {

    fun signIn(email: String, password: String): Single<Boolean>
    fun signUp(data: SignUpData): Single<User>

}