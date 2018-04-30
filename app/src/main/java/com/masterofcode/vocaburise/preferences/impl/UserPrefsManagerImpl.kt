package com.masterofcode.vocaburise.preferences.impl

import com.chibatching.kotpref.KotprefModel
import com.masterofcode.vocaburise.api.ApiRepository
import com.masterofcode.vocaburise.api.IApiRepository
import com.masterofcode.vocaburise.api.bodies.SignUpData
import com.masterofcode.vocaburise.models.User
import com.masterofcode.vocaburise.preferences.IUserPrefsManager
import io.reactivex.Single

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

    override fun signIn(email: String, password: String): Single<Boolean> {
        return apiRepo.signIn(email, password)
    }

    override fun signUp(data: SignUpData): Single<User> {
        return apiRepo.signUp(data)
    }
}