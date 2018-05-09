package com.masterofcode.vocaburise.screens

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.masterofcode.vocaburise.R
import com.masterofcode.vocaburise.base.BaseBoundVmActivity
import com.masterofcode.vocaburise.databinding.ActivityMainBinding
import com.masterofcode.vocaburise.preferences.UserPrefsManager
import com.masterofcode.vocaburise.screens.addWord.AddWordActivity
import com.masterofcode.vocaburise.screens.auth.AuthActivity
import com.masterofcode.vocaburise.screens.auth.AuthState
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

class MainActivity : BaseBoundVmActivity<ActivityMainBinding, MainActivityViewModel>(
        R.layout.activity_main, MainActivityViewModel::class), MainActivityInteractor {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        vm.init(this)

        if (!UserPrefsManager.isLoggedIn()) {
            AuthActivity.start(this, AuthState.SIGN_IN)
            finish()
            return
        }

        fab.setOnClickListener { view ->
            AddWordActivity.start(this)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_signOut -> {
                showErrorSnackbar("TODO", null)
                return true
            }
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun showErrorSnackbar(message: String, throwable: Throwable?) {
        showErrorSnackbar(find(R.id.contentView), message, throwable)
    }

    companion object {
        fun start(activity: Activity) {
            activity.startActivity<MainActivity>()
        }
    }
}
