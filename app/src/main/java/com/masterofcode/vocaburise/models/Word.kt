package com.masterofcode.vocaburise.models

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class Word(val id: Int? = null,
                val word: String,
                val translation: String,
                val dueDate: String? = null,
                val userId: Int? = null,
                val categoryId: Int? = null) : Parcelable