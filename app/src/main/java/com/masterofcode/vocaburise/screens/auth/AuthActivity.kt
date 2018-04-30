package com.masterofcode.vocaburise.screens.auth

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.masterofcode.vocaburise.R
import com.masterofcode.vocaburise.base.BaseActivity
import com.masterofcode.vocaburise.screens.auth.sign_in.SignInFragment
import org.jetbrains.anko.startActivity

/**
 * Created by andrews on 24.04.18.
 */

enum class AuthState {
    REGISTER, SIGN_IN
}

class AuthActivity : BaseActivity(R.layout.activity_auth), AuthInteractor {

    private val signInFragment = SignInFragment()
    private val registerFragment = Fragment()

    private fun getFragment(state: AuthState): Fragment = when (state) {
        AuthState.REGISTER -> registerFragment
        AuthState.SIGN_IN -> signInFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val authState = AuthState.valueOf(intent.extras.get(EXTRA_STATE).toString())
        val fragment = getFragment(authState)
        fragment.replaceAndCommit(R.id.fragmentContainer, false)
    }

    companion object {
        const val EXTRA_STATE = "EXTRA_STATE"

        fun start(activity: Activity, state: AuthState) {
            activity.startActivity<AuthActivity>(EXTRA_STATE to state)
        }
    }
}