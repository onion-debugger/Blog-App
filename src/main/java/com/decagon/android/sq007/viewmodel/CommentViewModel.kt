package com.decagon.android.sq007.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.android.sq007.model.CommentDataModel
import com.decagon.android.sq007.network.PostApi
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentViewModel : ViewModel() {
    private val _response = MutableLiveData<List<CommentDataModel>>()

    val response: LiveData<List<CommentDataModel>>
        get() = _response

    private val _newComment = MutableLiveData<CommentDataModel>()

    val newComment: LiveData<CommentDataModel>
        get() = _newComment



    /**
     * [getComments] returns the list of comments from the API service
     * @param id is used to get the comment related to that [id]
     * */
    fun getComments(id : Int) {
        PostApi.postService.getComments(id).enqueue(
            object: Callback<List<CommentDataModel>> {
                override fun onResponse(
                    call: Call<List<CommentDataModel>>,
                    response: Response<List<CommentDataModel>>) {
                    _response.value = response.body()
                }

                override fun onFailure(call: Call<List<CommentDataModel>>, t: Throwable) {
                }

            }
        )
    }

    /**
     * [addComment] function is called to add new comment to the list of comment
     * @param id is used to append the new comment to post
     * @param comment is the comment to be added to the list
     * */
    fun addComment(id : Int, comment: CommentDataModel) {
        viewModelScope.launch {
            _newComment.value =  PostApi.postService.addComments(id, comment)
        }
    }
}