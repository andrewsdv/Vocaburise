package com.masterofcode.vocaburise.screens.auth.sign_in

import com.masterofcode.vocaburise.R
import com.masterofcode.vocaburise.base.BaseBoundVmFragment
import com.masterofcode.vocaburise.databinding.FragmentSignInBinding

/**
 * Created by andrews on 24.04.18.
 */
class SignInFragment : BaseBoundVmFragment<FragmentSignInBinding, SignInViewModel>(
        R.layout.fragment_sign_in, SignInViewModel::class), SignInInteractor {

    override fun register() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun finish() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showErrorSnackbar(message: String, throwable: Throwable?) {
        showErrorSnackbar(binding.contentView, message, throwable)
    }
}