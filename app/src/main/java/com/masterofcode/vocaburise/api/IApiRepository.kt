package com.masterofcode.vocaburise.api

import io.reactivex.Single

/**
 * Created by andrews on 30.04.18.
 */
interface IApiRepository {

    fun signIn(email: String, password: String): Single<Boolean>

}