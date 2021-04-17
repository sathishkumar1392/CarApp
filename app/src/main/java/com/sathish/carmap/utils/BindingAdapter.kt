package com.sathish.carmap.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.sathish.carmap.R


@BindingAdapter("image")
fun setImageUrl(view: ImageView, imageUrl: String) {
    imageUrl.let {
        Glide.with(view).load(imageUrl).placeholder(R.drawable.preview).into(view)
    }
}


@BindingAdapter("visible")
fun progressVisibility(view: View, visibleStatus: Boolean) = when {
    visibleStatus -> {
        view.visibility = View.VISIBLE
    }
    else -> {
        view.visibility = View.GONE
    }
}