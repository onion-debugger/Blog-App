package com.decagon.android.sq007.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.R
import com.decagon.android.sq007.databinding.PostViewLayoutBinding
import com.decagon.android.sq007.model.PostDataModel
import com.decagon.android.sq007.network.PostCardClick

class PostsAdapter(private val listener : PostCardClick)
    : RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    private var postsList =  arrayListOf<PostDataModel>()

    inner class PostsViewHolder(private val binding: PostViewLayoutBinding)
        : RecyclerView.ViewHolder(binding.root) {
        var bindTitle = binding.postTitle
        var bindBody = binding.postBody
        var bindCard = binding.card
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val binding = DataBindingUtil.inflate<PostViewLayoutBinding>(
            LayoutInflater.from(parent.context),
            R.layout.post_view_layout, parent, false )
        return PostsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        holder.bindTitle.text = postsList[position].title
        holder.bindBody.text = postsList[position].body

        holder.bindCard.setOnClickListener {
            listener.onCardClick(postsList[position])
        }

    }

    override fun getItemCount(): Int = postsList.size

    fun addAllPost(posts : ArrayList<PostDataModel>){
        this.postsList = posts
        notifyDataSetChanged()
    }

}