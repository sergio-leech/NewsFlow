package com.example.newsflow.features.news_feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
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
        fun bind(_article: Article) {
            binding.apply {
                article = _article
            }
        }
    }

  private  companion object DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }
    }

}