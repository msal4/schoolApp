package com.smart.resources.schools_app.core.bindingAdapters

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.smart.resources.schools_app.R


@BindingAdapter("android:srcUrl")
fun loadImageUrl(iv: ImageView, url: String?) {
    loadUrl(url, iv)
}

private fun loadUrl(url: String?, iv: ImageView) {
    if (url == "") {
        Glide
            .with(iv.context)
            .clear(iv)
    } else {
        Glide
            .with(iv.context)
            .load(url)
            .into(iv)
    }
}

@BindingAdapter("android:accountUrl")
fun setAccountImage(iv: ImageView, url: String?) {
    //iv.context.toast(url.toString())

    if(url==""){
        Glide
            .with(iv.context)
            .load(R.drawable.ic_profile_place_holder)
            .into(iv)
    }else {

        Glide
            .with(iv.context)
            .load(url)
            .into(iv)
    }
}

@BindingAdapter("mine:setImage")
fun loadImageDrawable(iv: ImageView, drawable: Drawable) {
    Glide
        .with(iv.context)
        .load(drawable)
        .into(iv)
}