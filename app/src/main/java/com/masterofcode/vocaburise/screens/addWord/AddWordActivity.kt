package com.masterofcode.vocaburise.screens.addWord

import com.masterofcode.vocaburise.R
import com.masterofcode.vocaburise.base.BaseBoundVmActivity
import com.masterofcode.vocaburise.databinding.ActivityAddWordBinding

class AddWordActivity : BaseBoundVmActivity<ActivityAddWordBinding, AddWordViewModel>(
        R.layout.activity_add_word, AddWordViewModel::class), AddWordInteractor {
}