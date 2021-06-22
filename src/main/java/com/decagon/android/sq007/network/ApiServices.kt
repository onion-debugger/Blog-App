package com.decagon.android.sq007.network

import com.decagon.android.sq007.constant.BASE_URL
import com.decagon.android.sq007.model.CommentDataModel
import com.decagon.android.sq007.model.PostDataModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

/**
 * Build the Moshi object that Retrofit will be using
 */

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Use the Retrofit builder to build a retrofit object
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/**
 * A public interface that exposes the [getPosts] method
 */
interface ApiServices {
    /**
     * The @GET annotation indicates that the "posts" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("posts")
    fun getPosts(): Call<List<PostDataModel>>


    /**
     * The @GET annotation indicates that the "comments" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("posts/{userId}/comments")
    fun getComments(@Path ("userId") userId : Int): Call<List<CommentDataModel>>

    @POST("posts")
    suspend fun addPost(@Body post: PostDataModel) : PostDataModel

    @POST("posts/{id}/comments")
    suspend fun addComments(@Path("id") id : Int , @Body comment: CommentDataModel) : CommentDataModel
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */

object PostApi{
    val postService: ApiServices by lazy {
        retrofit.create(ApiServices::class.java)
    }
}