package com.example.newsflow.features.news_feature.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsflow.R
import com.example.newsflow.common.NavigationKeyArgs
import com.example.newsflow.common.models.Article
import com.example.newsflow.databinding.NewsItemBinding

class NewsPagingAdapter() :
    PagingDataAdapter<Article, NewsPagingAdapter.NewsPagingViewHolder>(DiffCallback) {

    override fun onBindViewHolder(holder: NewsPagingViewHolder, position: Int) {
        getItem(position)?.let { article ->
            holder.bind(article)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsPagingViewHolder {
        return NewsPagingViewHolder(
            NewsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    class NewsPagingViewHolder(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setOnClickListener { view ->
                binding.article?.let { article ->
                    navigateToNews(article, view)
                }
            }
        }

        private fun navigateToNews(article: Article, view: View) {
            val arg = bundleOf(NavigationKeyArgs.ARTICLE to article.url)
            view.findNavController().navigate(R.id.action_newsFragment_to_detailNewsFragment, arg)
        }

        fun bind(_article: Article) {
            binding.apply {
                article = _article
                executePendingBindings()
            }
        }
    }

    private companion object DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }
    }
}