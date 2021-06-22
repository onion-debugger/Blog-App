package com.decagon.android.sq007.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.R
import com.decagon.android.sq007.databinding.CommentViewLayoutBinding
import com.decagon.android.sq007.model.CommentDataModel

class CommentsAdapter
    : RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>() {

    private var commentList = arrayListOf<CommentDataModel>()

    inner class CommentViewHolder(private val binding: CommentViewLayoutBinding)
        : RecyclerView.ViewHolder(binding.root) {
        var bindName = binding.commentName
        var bindEmail = binding.commentEmail
        var bindComment = binding.commentBody
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = DataBindingUtil.inflate<CommentViewLayoutBinding>(
            LayoutInflater.from(parent.context),
            R.layout.comment_view_layout, parent, false
        )
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bindName.text = commentList[position].name
        holder.bindEmail.text = commentList[position].email
        holder.bindComment.text = commentList[position].body
    }

    override fun getItemCount(): Int = commentList.size

    fun addAllComment(comments : ArrayList<CommentDataModel>){
        this.commentList = comments
        notifyDataSetChanged()
    }

}