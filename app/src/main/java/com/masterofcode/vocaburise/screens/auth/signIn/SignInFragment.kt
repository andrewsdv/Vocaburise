package com.masterofcode.vocaburise.screens.auth.signIn

import android.os.Bundle
import android.view.View
import com.masterofcode.vocaburise.screens.MainActivity
import com.masterofcode.vocaburise.R
import com.masterofcode.vocaburise.base.BaseBoundVmFragment
import com.masterofcode.vocaburise.databinding.FragmentSignInBinding
import com.masterofcode.vocaburise.screens.auth.signUp.SignUpFragment

/**
 * Created by andrews on 24.04.18.
 */
class SignInFragment : BaseBoundVmFragment<FragmentSignInBinding, SignInViewModel>(
        R.layout.fragment_sign_in, SignInViewModel::class), SignInInteractor {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.init(this)
    }

    override fun register() {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragmentContainer, SignUpFragment())
        transaction?.commitAllowingStateLoss()
    }

    override fun openWordsScreen() {
        MainActivity.start(activity!!)
        activity?.finish()
    }

    override fun showErrorSnackbar(message: String, throwable: Throwable?) {
        showErrorSnackbar(binding.contentView, message, throwable)
    }
}