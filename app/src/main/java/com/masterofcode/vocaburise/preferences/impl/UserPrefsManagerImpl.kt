package com.masterofcode.vocaburise.preferences.impl

import com.chibatching.kotpref.KotprefModel
import com.masterofcode.vocaburise.api.ApiRepository
import com.masterofcode.vocaburise.api.IApiRepository
import com.masterofcode.vocaburise.api.bodies.SignUpData
import com.masterofcode.vocaburise.errorHandling.NetworkErrorWrapper
import com.masterofcode.vocaburise.models.User
import com.masterofcode.vocaburise.preferences.IUserPrefsManager
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.adapter.rxjava2.Result

/**
 * Created by andrews on 24.04.18.
 */

private object UserPrefs : KotprefModel() {

    var name by nullableStringPref()
    var email by nullableStringPref()
    var accessToken by nullableStringPref()

}

class UserPrefsManagerImpl : IUserPrefsManager {

    private val apiRepo: IApiRepository = ApiRepository

    override fun getName() = UserPrefs.name ?: ""

    override fun getEmail() = UserPrefs.email ?: ""

    override val accessToken: String?
        get() = UserPrefs.accessToken

    override fun isLoggedIn(): Boolean {
        return UserPrefs.accessToken != null
    }

    override fun signIn(email: String, password: String): Observable<Result<User>> {
        return Observable.defer { apiRepo.signIn(email, password) }
                .doAfterNext {
                    if (it.response().errorBody() != null) {
                        val message = "${it.response().code()} ${it.response().message()}"
                        throw NetworkErrorWrapper(message)
                    }
                    UserPrefs.accessToken = it.response().headers().get("Authorization")
                    UserPrefs.name = it.response().body().name
                    UserPrefs.email = it.response().body().email
                }
    }

    override fun signUp(data: SignUpData): Observable<Result<User>> {
        return Observable.defer { apiRepo.signUp(data) }
                .doAfterNext {
                    if (it.response().errorBody() != null) {
                        val message = "${it.response().code()} ${it.response().message()}"
                        throw NetworkErrorWrapper(message)
                    }
                    UserPrefs.accessToken = it.response().headers().get("Authorization")
                }
    }

    override fun signOut(): Completable {
        return Completable.defer { apiRepo.signOut() }
                .doOnComplete {
                    UserPrefs.accessToken = null
                }
    }
}