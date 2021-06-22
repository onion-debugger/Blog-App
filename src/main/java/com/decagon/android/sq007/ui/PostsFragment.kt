package com.decagon.android.sq007.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.decagon.android.sq007.viewmodel.PostsViewModel
import com.decagon.android.sq007.R
import com.decagon.android.sq007.databinding.PostsFragmentBinding
import com.decagon.android.sq007.network.PostCardClick
import com.decagon.android.sq007.adapter.PostsAdapter
import com.decagon.android.sq007.model.PostDataModel

class PostsFragment : Fragment(), PostCardClick {

    private lateinit var binding: PostsFragmentBinding

    private lateinit var viewModel: PostsViewModel
    private lateinit var postAdapter: PostsAdapter

//    private val args: PostsFragmentArgs by navArgs()

//    private var postModel: PostDataModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.posts_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(PostsViewModel::class.java)

        viewModel.getPosts()

        postAdapter = PostsAdapter(this)


        /**
         * The [viewModel] observe the data coming from the API call
         * */
        viewModel.response.observe(viewLifecycleOwner, {
            val response = it as ArrayList<PostDataModel>
            viewModel.newPost.observe(viewLifecycleOwner,{newPost->
                response.add(0, newPost)
            })
            postAdapter.addAllPost(response)
            binding.postsRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = postAdapter
            }
        })

        /**
         * Querying the list of post
         * */
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.searchPostList(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    viewModel.searchPostList(newText)
                }
                return true
            }

        })

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.addPostFragment)
        }
    }

    /**
     * Using navigation Controller to move across fragments
     * */
    override fun onCardClick(postDataModel : PostDataModel) {
        val action = PostsFragmentDirections.actionPostsFragmentToCommentFragment(postDataModel, null)
        findNavController().navigate(action)
    }

}