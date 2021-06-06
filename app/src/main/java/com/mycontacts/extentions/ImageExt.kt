package com.mycontacts.extentions

import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


fun ImageView.loadCircleCropImage(url: String) {

    val circularProgressDrawable = CircularProgressDrawable(this.context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 20f
    circularProgressDrawable.start()

    Glide.with(context)
        .applyDefaultRequestOptions(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
        .load(url).circleCrop()
        .placeholder(circularProgressDrawable).into(this)
}

fun ImageView.cache(url: String?) {
    Glide.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .preload();
}

fun ImageView.loadImageWithLoadingPlaceHolder(url: String?) {
    val circularProgressDrawable = CircularProgressDrawable(this.context)
    circularProgressDrawable.strokeWidth = 10f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    Glide.with(context)
        .applyDefaultRequestOptions(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
        .load(url)
        .placeholder(circularProgressDrawable)
        .into(this)
}



