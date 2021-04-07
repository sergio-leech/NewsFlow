package com.example.newsflow.features.news_feature.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
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

        renderNewsFragment()
        setUiEffect()

        return binding.root
    }

    private fun renderNewsFragment() {
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest { state ->
                    when (state.newsState) {
                        is NewsContract.NewsState.Idle -> {
                            binding.progressBar.visibility = View.GONE
                        }
                        is NewsContract.NewsState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is NewsContract.NewsState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            adapter.submitData(state.newsState.news)
                        }
                        is NewsContract.NewsState.Error -> {
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                }
        }
    }

    private fun setUiEffect() {
        lifecycleScope.launch {
            viewModel.uiEffect.collect { effect ->
                when (effect) {
                    is NewsContract.Effect.OnShowToast -> {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}