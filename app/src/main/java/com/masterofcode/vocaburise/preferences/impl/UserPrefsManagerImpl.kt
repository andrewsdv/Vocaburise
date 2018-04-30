package com.masterofcode.vocaburise.preferences.impl

import com.chibatching.kotpref.KotprefModel
import com.masterofcode.vocaburise.preferences.IUserPrefsManager
import io.reactivex.Single

/**
 * Created by andrews on 24.04.18.
 */

private object UserPrefs : KotprefModel() {

    var token by nullableStringPref()

}

class UserPrefsManagerImpl : IUserPrefsManager {

    override fun isLoggedIn(): Boolean {
        return UserPrefs.token != null
    }

    override fun signIn(login: String, password: String): Single<Unit> {
        return Single.just(Unit)
    }
}