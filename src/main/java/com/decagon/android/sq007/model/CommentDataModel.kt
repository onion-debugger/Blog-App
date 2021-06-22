package com.decagon.android.sq007.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommentDataModel(
    var body: String,
    val email: String = "axlestano@gmail.com",
    var id: Int = 0,
    val name: String = "Mubarak Yaqoub",
    val postId: Int = 0
) : Parcelable