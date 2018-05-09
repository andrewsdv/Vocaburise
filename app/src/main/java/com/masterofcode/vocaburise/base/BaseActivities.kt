package com.masterofcode.vocaburise.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.View
import com.masterofcode.vocaburise.BR
import com.masterofcode.vocaburise.preferences.UserPrefsManager
import com.masterofcode.vocaburise.screens.auth.AuthActivity
import com.masterofcode.vocaburise.screens.auth.AuthState
import com.masterofcode.vocaburise.utils.toast
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import io.reactivex.Maybe
import io.reactivex.subjects.PublishSubject
import retrofit2.HttpException
import kotlin.reflect.KClass

/**
 * Created by andrews on 24.04.18.
 */

data class ActivityResult(
        val requestCode: Int,
        val resultCode: Int,
        val data: Intent?
)

abstract class BaseActivity(
        private val layoutId: Int? = null
) : RxAppCompatActivity() {
    private val activityResults = PublishSubject.create<ActivityResult>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutId?.let { setContentLayout(it) }
    }

    final override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        activityResults.onNext(ActivityResult(requestCode, resultCode, data))
    }

    protected fun awaitActivityResult(requestCode: Int): Maybe<ActivityResult> = activityResults.filter { it.requestCode == requestCode }.firstElement()

    protected open fun setContentLayout(@LayoutRes layoutId: Int) {
        setContentView(layoutId)
    }

    fun Fragment.replaceAndCommit(@IdRes containerId: Int, addToBackStack: Boolean = false, tag: String? = null) {
        var transaction = supportFragmentManager.beginTransaction()
                .replace(containerId, this, tag)

        if (addToBackStack)
            transaction = transaction.addToBackStack(tag)

        if (addToBackStack)
            transaction.commit()
        else
            transaction.commitNow()
    }

    fun showErrorSnackbar(view: View, message: String, throwable: Throwable?) {
        if ((throwable as HttpException).code() == 401) {
            toast("Token expired...")
            UserPrefsManager.clearAccessToken()
            AuthActivity.start(this, AuthState.SIGN_IN)
            finish()
        } else {
            Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                    .setAction(android.R.string.ok, { }).show()
        }
    }
}

abstract class BaseBoundActivity<out TBinding : ViewDataBinding>(
        layoutId: Int,
        private val disableTransitions: Boolean = false
) : BaseActivity(layoutId) {
    private lateinit var innerBinding: TBinding
    protected val binding: TBinding by lazy { innerBinding }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (disableTransitions) overridePendingTransition(0, 0)
    }

    override fun setContentLayout(layoutId: Int) {
        innerBinding = DataBindingUtil.setContentView(this, layoutId)
    }
}

abstract class BaseBoundVmActivity<out TBinding : ViewDataBinding, out TViewModel : ViewModel>(
        layoutId: Int,
        private val vmClass: KClass<TViewModel>,
        private val autoBindVm: Boolean = true,
        disableTransitions: Boolean = false
) : BaseBoundActivity<TBinding>(layoutId, disableTransitions) {
    protected val vm: TViewModel by lazy { ViewModelProviders.of(this).get(vmClass.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (autoBindVm) binding.setVariable(BR.vm, vm)
    }
}