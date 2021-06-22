package com.decagon.android.sq007.network

import com.decagon.android.sq007.model.PostDataModel

interface PostCardClick {
    /**
     * Set an onclickListener on [onCardClick]
     * */
    fun onCardClick(postDataModel: PostDataModel)
}