package com.example.newsflow.features.detail_news_feature.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.newsflow.databinding.FragmentDetailNewsBinding
import com.example.newsflow.features.detail_news_feature.view_model.DetailNewsContract
import com.example.newsflow.features.detail_news_feature.view_model.DetailNewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailNewsFragment : Fragment() {
    private val viewModel: DetailNewsViewModel by viewModels()
    lateinit var binding: FragmentDetailNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailNewsBinding.inflate(inflater, container, false)
        renderDetailNewsFragment()
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun renderDetailNewsFragment() {
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { state ->
                    when (state.newsState) {
                        is DetailNewsContract.NewsState.Idle -> {
                            binding.progressBar.visibility = View.GONE
                        }
                        is DetailNewsContract.NewsState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is DetailNewsContract.NewsState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.webViewNews.apply {
                                loadUrl(state.newsState.url)
                                settings.javaScriptEnabled = true
                                webViewClient = WebViewClient()
                            }
                        }
                        is DetailNewsContract.NewsState.Error -> {
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                }
        }
    }

}