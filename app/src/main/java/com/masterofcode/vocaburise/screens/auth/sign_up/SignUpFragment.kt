package com.masterofcode.vocaburise.screens.auth.sign_up

import android.os.Bundle
import android.view.View
import com.masterofcode.vocaburise.MainActivity
import com.masterofcode.vocaburise.R
import com.masterofcode.vocaburise.base.BaseBoundVmFragment
import com.masterofcode.vocaburise.databinding.FragmentSignUpBinding

class SignUpFragment  : BaseBoundVmFragment<FragmentSignUpBinding, SignUpViewModel>(
        R.layout.fragment_sign_up, SignUpViewModel::class), SignUpInteractor {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.init(this)
    }

    override fun openWordsScreen() {
        MainActivity.start(activity!!)
        activity?.finish()
    }

    override fun showErrorSnackbar(message: String, throwable: Throwable?) {
        showErrorSnackbar(binding.contentView, message, throwable)
    }
}