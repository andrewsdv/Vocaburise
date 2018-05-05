package com.masterofcode.vocaburise.screens.addWord

import android.databinding.Bindable
import com.masterofcode.vocaburise.BR
import com.masterofcode.vocaburise.base.BaseViewModel

class AddWordViewModel : BaseViewModel() {

    var progressBarVisible: Boolean = false
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.progressBarVisible)
        }
}