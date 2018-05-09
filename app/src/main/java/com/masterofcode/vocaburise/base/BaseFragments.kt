package com.masterofcode.vocaburise.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.masterofcode.vocaburise.BR
import com.masterofcode.vocaburise.preferences.UserPrefsManager
import com.masterofcode.vocaburise.screens.auth.AuthActivity
import com.masterofcode.vocaburise.screens.auth.AuthState
import com.masterofcode.vocaburise.utils.toast
import com.trello.rxlifecycle2.components.support.RxFragment
import io.reactivex.Maybe
import io.reactivex.subjects.PublishSubject
import retrofit2.HttpException
import kotlin.reflect.KClass

/**
 * Created by andrews on 24.04.18.
 */
abstract class BaseFragment(
        protected val layoutId: Int
) : RxFragment() {
    private val activityResults = PublishSubject.create<ActivityResult>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(layoutId, container, false)
    }

    final override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        activityResults.onNext(ActivityResult(requestCode, resultCode, data))
    }

    protected fun awaitActivityResult(requestCode: Int): Maybe<ActivityResult> = activityResults.filter { it.requestCode == requestCode }.firstElement()

    protected inline fun <reified TViewModel: ViewModel> getActivityViewModel(): TViewModel {
        return ViewModelProviders.of(activity!!).get(TViewModel::class.java)
    }

    fun showErrorSnackbar(view: View, message: String, throwable: Throwable? = null) {
        if (throwable is HttpException && throwable.code() == 401) {
            toast("Token expired...")
            UserPrefsManager.clearAccessToken()
            AuthActivity.start(activity!!, AuthState.SIGN_IN)
            activity?.finish()
        } else {
            Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                    .setAction(android.R.string.ok, { }).show()
        }
    }
}

abstract class BaseBoundFragment<out TBinding : ViewDataBinding>(layoutId: Int) : BaseFragment(layoutId) {
    private lateinit var innerBinding: TBinding
    protected val binding
        get() = innerBinding

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(layoutId, container, false)
        DataBindingUtil.bind<TBinding>(view)?.let {
            innerBinding = it
        }
        return view
    }
}

abstract class BaseBoundVmFragment<out TBinding : ViewDataBinding, out TViewModel : ViewModel>(
        layoutId: Int,
        private val vmClass: KClass<TViewModel>,
        private val autoBindVm: Boolean = true
) : BaseBoundFragment<TBinding>(layoutId) {
    protected val vm: TViewModel by lazy { ViewModelProviders.of(this).get(vmClass.java) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (autoBindVm) binding.setVariable(BR.vm, vm)
    }
}