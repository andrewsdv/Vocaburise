package com.masterofcode.vocaburise.api

import com.masterofcode.vocaburise.api.bodies.SignUpData
import com.masterofcode.vocaburise.models.User
import com.masterofcode.vocaburise.models.Word
import com.masterofcode.vocaburise.utils.Constants.DEFAULT_CATEGORY_ID
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

/**
 * Created by andrews on 30.04.18.
 */
interface IApiRepository {
    fun signIn(email: String, password: String): Observable<Result<User>>
    fun signUp(data: SignUpData): Observable<Result<User>>
    fun signOut(): Completable

    fun addWord(word: Word): Single<Boolean>
    fun getWords(): Single<List<Word>>
}