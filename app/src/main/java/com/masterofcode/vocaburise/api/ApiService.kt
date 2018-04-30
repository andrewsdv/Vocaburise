package com.masterofcode.vocaburise.api

import io.reactivex.Single
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by andrews on 30.04.18.
 */
interface ApiService {

    @POST("users/login")
    fun signIn(@Query(encoded = true, value = "email") email: String, @Query("password") password: String): Single<Boolean>

}