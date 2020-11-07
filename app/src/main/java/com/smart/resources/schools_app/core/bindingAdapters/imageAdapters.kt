package com.smart.resources.schools_app.core.bindingAdapters

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.haytham.coder.extensions.toDrawable
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.R

@BindingAdapter("android:srcUrl", "android:placeHolder",  "android:onFinished", requireAll = false)
fun ImageView.loadImageUrl(url: String?, placeholder: Drawable?=null, onFinished:(()->Unit)?= null) {
    Logger.d("srcUrl: $url")

    val listener = object : RequestListener<Drawable> {


        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            onFinished?.invoke()
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            onFinished?.invoke()
            return false
        }
    }

    Glide
        .with(context)
        .load(url)
        .apply {
            if (placeholder != null) placeholder(placeholder)
            else placeholder(R.drawable.img_place_holder)
        }
        .addListener(listener)
        .into(this)
}


@BindingAdapter("android:accountUrl")
fun setAccountImage(iv: ImageView, url: String?) {
    //iv.context.toast(url.toString())

    if (url == "") {
        Glide
            .with(iv.context)
            .load(R.drawable.ic_profile_place_holder)
            .into(iv)
    } else {

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

@BindingAdapter("android:srcDrawableId")
fun ImageView.setSrcDrawableId(@DrawableRes drawableId: Int?) {
    if (drawableId != null) {
        setImageDrawable(drawableId.toDrawable(context))
    }
}
