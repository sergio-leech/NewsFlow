package com.example.newsflow.common.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
):Parcelable