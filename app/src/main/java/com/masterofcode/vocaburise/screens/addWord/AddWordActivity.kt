package com.masterofcode.vocaburise.screens.addWord

import android.app.Activity
import com.masterofcode.vocaburise.R
import com.masterofcode.vocaburise.base.BaseBoundVmActivity
import com.masterofcode.vocaburise.databinding.ActivityAddWordBinding
import org.jetbrains.anko.startActivity

class AddWordActivity : BaseBoundVmActivity<ActivityAddWordBinding, AddWordViewModel>(
        R.layout.activity_add_word, AddWordViewModel::class), AddWordInteractor {

    override fun showErrorSnackbar(message: String) {
        showErrorSnackbar(binding.contentView, message)
    }

    companion object {
        fun start(activity: Activity) {
            activity.startActivity<AddWordActivity>()
        }
    }

}