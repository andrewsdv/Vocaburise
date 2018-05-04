package com.masterofcode.vocaburise.preferences.impl

import com.chibatching.kotpref.KotprefModel
import com.masterofcode.vocaburise.api.ApiRepository
import com.masterofcode.vocaburise.api.IApiRepository
import com.masterofcode.vocaburise.api.bodies.SignUpData
import com.masterofcode.vocaburise.models.User
import com.masterofcode.vocaburise.preferences.IUserPrefsManager
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

/**
 * Created by andrews on 24.04.18.
 */

private object UserPrefs : KotprefModel() {

    var accessToken by nullableStringPref()

}

class UserPrefsManagerImpl : IUserPrefsManager {

    private val apiRepo: IApiRepository = ApiRepository()

    override val accessToken: String?
        get() = UserPrefs.accessToken

    override fun isLoggedIn(): Boolean {
        return UserPrefs.accessToken != null
    }

    override fun signIn(email: String, password: String): Observable<Result<User>> {
        return Observable.defer { apiRepo.signIn(email, password) }
                .doAfterNext { UserPrefs.accessToken = it.response().headers().get("Authorization") }
    }

    override fun signUp(data: SignUpData): Single<User> {
        return apiRepo.signUp(data)
    }
}