package com.masterofcode.vocaburise.api

import com.masterofcode.vocaburise.api.bodies.SignUpData
import com.masterofcode.vocaburise.models.User
import io.reactivex.Observable
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by andrews on 30.04.18.
 */
interface ApiService {

    @GET("users/login")
    fun signIn(@Query(encoded = true, value = "email") email: String,
               @Query("password") password: String): Observable<Result<User>>

    @POST("users/register")
    fun signUp(@Body data: SignUpData): Observable<Result<User>>

}