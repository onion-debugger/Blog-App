package com.decagon.android.sq007.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.decagon.android.sq007.R
import com.decagon.android.sq007.databinding.FragmentAddPostBinding
import com.decagon.android.sq007.model.PostDataModel
import com.decagon.android.sq007.viewmodel.PostsViewModel


class AddPostFragment : Fragment() {

    private lateinit var viewModel: PostsViewModel
    private var _binding: FragmentAddPostBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(PostsViewModel::class.java)

        /**
         * Adding a new Post to the List of Post
         * */
        binding.fabPostButton.setOnClickListener {
            val postBody = binding.body.text.toString()
            val postTitle = binding.title.text.toString()

            viewModel.addPost(PostDataModel(body = postBody, 0, title = postTitle, 1))

            /**
             * [viewModel] observes the new Post being added into the list of post
             * */
            viewModel.newPost.observe(viewLifecycleOwner, Observer {
                Log.d("TAG", "onViewCreated: $it")
                Toast.makeText(requireContext(), "response $it", Toast.LENGTH_LONG).show()
                binding.body.text?.clear()
                binding.title.text?.clear()
                binding.body.clearFocus()
                binding.title.clearFocus()
                findNavController().navigate(R.id.postsFragment)
            })


        }

    }

}