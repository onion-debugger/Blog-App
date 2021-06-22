package com.decagon.android.sq007.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.android.sq007.model.PostDataModel
import com.decagon.android.sq007.network.PostApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostsViewModel : ViewModel() {

    private val _response = MutableLiveData<List<PostDataModel>>()

    val response: LiveData<List<PostDataModel>>
        get() = _response

    private val _newPost = MutableLiveData<PostDataModel>()

    val newPost: LiveData<PostDataModel>
        get() = _newPost


    init {
        getPosts()
    }

    /**
     * The function [getPosts] return the list of post from the API
     * */
    fun getPosts() {
        PostApi.postService.getPosts().enqueue(
            object: Callback<List<PostDataModel>> {
                override fun onResponse(
                    call: Call<List<PostDataModel>>, response: Response<List<PostDataModel>>) {
                    _response.value = response.body()
                }

                override fun onFailure(call: Call<List<PostDataModel>>, t: Throwable) {

                }
            })
    }


    /**
     * [addPost] returns the new list of Post with the added new Post
     * @param posts is the post to be added to the list of post
     * */
    fun addPost(posts: PostDataModel) {
        viewModelScope.launch {
            _newPost.value = PostApi.postService.addPost(posts)
            Log.d("addPost", "addPost: ${_newPost.value!!.title} ${_newPost.value!!.body}")
        }
    }

    /**
     * [searchPostList] returns the list of post based on query
     * @param query is the input used for the filtering
     * */
    private var storedPostList = MutableLiveData<List<PostDataModel>>()
    private var isSearchStarting = true
    var isSearching = MutableStateFlow(false)

    fun searchPostList(query: String) {

        if (isSearchStarting) {
            storedPostList.value = _response.value
            isSearchStarting = false
        }

        val listPostToSearch = if (isSearchStarting) {
            response.value
        } else {
            storedPostList.value
        }

        viewModelScope.launch {
            if (query.isEmpty()) {
                _response.value = storedPostList.value
                isSearching.value = false
                isSearchStarting = true
                return@launch
            } else {
                val result = listPostToSearch?.filter {
                    it.title.contains(query.trim(), true) ||
                            it.id.toString().contains(query.trim())
                }
                result?.let {
                    _response.value = result!!
                }
            }
        }

        if (isSearchStarting) {
            storedPostList.value = _response.value
            isSearchStarting = false
        }

        isSearching.value = true
    }
}