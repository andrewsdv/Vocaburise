package com.masterofcode.vocaburise.api

import com.masterofcode.vocaburise.api.bodies.SignUpData
import com.masterofcode.vocaburise.models.User
import com.masterofcode.vocaburise.models.Word
import com.masterofcode.vocaburise.utils.Constants
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.*

/**
 * Created by andrews on 30.04.18.
 */
interface ApiService {

    @GET("users/login")
    fun signIn(@Query(encoded = true, value = "email") email: String,
               @Query("password") password: String): Observable<Result<User>>

    @POST("users/register")
    fun signUp(@Body data: SignUpData): Observable<Result<User>>

    @GET("users/logout")
    fun signOut(): Completable

    @POST("words/")
    fun addWord(@Body word: Word): Single<Boolean>

    @GET("words/{id_category}")
    fun getWords(@Path("id_category") categoryId: Int = Constants.DEFAULT_CATEGORY_ID): Single<List<Word>>
}