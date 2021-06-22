package com.decagon.android.sq007.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.decagon.android.sq007.R
import com.decagon.android.sq007.databinding.AddCommentFragmentBinding
import com.decagon.android.sq007.model.CommentDataModel
import com.decagon.android.sq007.model.PostDataModel
import com.decagon.android.sq007.viewmodel.CommentViewModel

class AddCommentFragment : Fragment() {

    private lateinit var binding: AddCommentFragmentBinding
    private val args : AddCommentFragmentArgs by navArgs()

    private lateinit var postModel : PostDataModel

    companion object {
        fun newInstance() = AddCommentFragment()
    }

    private lateinit var viewModel: CommentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.add_comment_fragment, container, false)
        postModel = args.postModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CommentViewModel::class.java)

        /**
         * Adding a new comment into the comment section
         * */
        binding.fabSaveButton.setOnClickListener {
            val commentBody = binding.body.text.toString()
                viewModel.addComment(postModel.id, CommentDataModel(body= commentBody))

                viewModel.newComment.observe(viewLifecycleOwner,{
                    val action = AddCommentFragmentDirections.actionAddCommentFragmentToCommentFragment(postModel, it)
                    binding.body.text?.clear()
                    binding.body.clearFocus()
                    findNavController().navigate(action)
                })
            }
    }
}