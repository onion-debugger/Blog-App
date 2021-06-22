package com.decagon.android.sq007.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.decagon.android.sq007.viewmodel.CommentViewModel
import com.decagon.android.sq007.R
import com.decagon.android.sq007.databinding.CommentFragmentBinding
import com.decagon.android.sq007.adapter.CommentsAdapter
import com.decagon.android.sq007.model.CommentDataModel
import com.decagon.android.sq007.model.PostDataModel

class CommentFragment : Fragment() {

    private lateinit var binding: CommentFragmentBinding
    private lateinit var viewModel: CommentViewModel

    private val args : CommentFragmentArgs by navArgs()
    private lateinit var postModel : PostDataModel
    private lateinit var commentsAdapter: CommentsAdapter
    private var commentModel : CommentDataModel? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.comment_fragment, container, false)
        postModel = args.postModel
        commentModel = args.commentModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(CommentViewModel::class.java)

        viewModel.getComments(postModel.id)

        binding.postTitle.text = postModel.title
        binding.postBody.text = postModel.body
        commentsAdapter = CommentsAdapter()

        /**
         * *Observing the response from the [CommentViewModel]
         * */
        viewModel.response.observe(
            viewLifecycleOwner, Observer {
                val response = it as ArrayList<CommentDataModel>
                commentModel?.let { newComment->
                    response.add(0, newComment)
                }
                commentsAdapter.addAllComment(response)
                binding.commentRecyclerView.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = commentsAdapter

                }
            }
        )

        binding.addComment.setOnClickListener {
            val action = CommentFragmentDirections.actionCommentFragmentToAddCommentFragment(postModel)
            findNavController().navigate(action)
        }
    }

}