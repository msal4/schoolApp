package com.smart.resources.schools_app.sharedUi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.bindingAdapters.loadImageUrl
import com.smart.resources.schools_app.databinding.ActivityImageViewerBinding


class ImageViewerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageViewerBinding

    companion object Factory{
        private const val EXTRA_IMAGE_URL= "extraImageUrl"

        fun newInstance(activity: Activity, imageView: ImageView, imageUrl: String){
            activity.apply {

                val intent= Intent(this, ImageViewerActivity::class.java)
                intent.putExtra(EXTRA_IMAGE_URL, imageUrl)

                val activityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity,
                        imageView, getString(R.string.image_trans)
                    )

               startActivity(intent, activityOptionsCompat.toBundle())
            }


        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=  DataBindingUtil.setContentView(this, R.layout.activity_image_viewer)

        val url= intent.getStringExtra(EXTRA_IMAGE_URL)
        loadImageUrl(
            binding.photoView,
            url
        )
    }

}
