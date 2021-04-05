package com.example.newsflow.features.news_feature.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation

@BindingAdapter("setNewsImage")
fun ImageView.bindNewsImage(url: String?) {
    url?.let { urlImage->
        load(urlImage) {
            crossfade(true)
        }
    }
}