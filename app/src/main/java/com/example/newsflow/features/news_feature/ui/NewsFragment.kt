package com.example.newsflow.features.news_feature.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.newsflow.R
import com.example.newsflow.databinding.FragmentNewsBinding
import com.example.newsflow.features.news_feature.adapter.NewsPagingAdapter
import com.example.newsflow.features.news_feature.view_model.NewsContract
import com.example.newsflow.features.news_feature.view_model.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : Fragment() {
    private val viewModel: NewsViewModel by viewModels()
    lateinit var binding: FragmentNewsBinding
    lateinit var adapter: NewsPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        adapter = NewsPagingAdapter()
        binding.newsRecyclerView.adapter = adapter
        lifecycleScope.launch {
            viewModel.list.collectLatest {data ->
                adapter.submitData(lifecycle,data)
            }
        }

        adapter.addLoadStateListener { state ->
            when(state.refresh){
                is LoadState.Loading ->{
                    binding.progressBar.visibility = View.VISIBLE
                }
                is LoadState.NotLoading ->{
                    binding.progressBar.visibility = View.GONE
                }
                is LoadState.Error ->{
                    binding.progressBar.visibility = View.GONE

                }
            }
        }

        return binding.root
    }


}