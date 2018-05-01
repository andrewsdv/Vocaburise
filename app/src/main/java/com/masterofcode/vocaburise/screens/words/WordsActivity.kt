package com.masterofcode.vocaburise.screens.words

import android.app.Activity
import com.masterofcode.vocaburise.base.BaseActivity
import com.masterofcode.vocaburise.screens.auth.AuthActivity
import org.jetbrains.anko.startActivity

class WordsActivity : BaseActivity() {

    companion object {
        fun start(activity: Activity) {
            activity.startActivity<AuthActivity>()
        }
    }
}