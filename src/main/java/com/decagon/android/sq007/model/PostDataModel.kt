package com.decagon.android.sq007.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostDataModel(
    var body: String,
    val id: Int,
    val title: String,
    val userId: Int
) : Parcelable