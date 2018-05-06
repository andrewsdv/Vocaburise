package com.masterofcode.vocaburise

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import com.masterofcode.vocaburise.R.id.contentView
import com.masterofcode.vocaburise.base.BaseActivity
import com.masterofcode.vocaburise.preferences.UserPrefsManager
import com.masterofcode.vocaburise.screens.addWord.AddWordActivity
import com.masterofcode.vocaburise.screens.auth.AuthActivity
import com.masterofcode.vocaburise.screens.auth.AuthState
import com.masterofcode.vocaburise.utils.async
import com.masterofcode.vocaburise.utils.strRes
import com.masterofcode.vocaburise.utils.toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!UserPrefsManager.isLoggedIn()) {
            AuthActivity.start(this, AuthState.SIGN_IN)
            finish()
            return
        }

        fab.setOnClickListener { view ->
            AddWordActivity.start(this)
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
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
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }

            R.id.sign_out -> {
                UserPrefsManager.signOut()
                        .async()
                        .subscribe({
                            toast(strRes(R.string.done))
                            AuthActivity.start(this, AuthState.SIGN_IN)
                            finish()
                        }, {
                            drawer_layout.closeDrawer(GravityCompat.START)
                            it.message?.let { showErrorSnackbar(find(contentView), it) }
                        }
                        )
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    companion object {
        fun start(activity: Activity) {
            activity.startActivity<MainActivity>()
        }
    }
}
