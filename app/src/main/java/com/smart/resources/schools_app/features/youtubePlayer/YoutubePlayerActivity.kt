package com.smart.resources.schools_app.features.youtubePlayer

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.haytham.coder.extensions.hideSystemUi
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.extentions.extractStartTimeFromUrl
import com.smart.resources.schools_app.core.extentions.extractVideoIdFromUrl
import com.smart.resources.schools_app.databinding.ActivityYoutubePlayerBinding


class YoutubePlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityYoutubePlayerBinding
    private val url: String? by lazy { intent.getStringExtra(EXTRA_URL) }
    private var mBackstackLost = false

    companion object {
        private const val EXTRA_URL = "extraYoutubeUrl"
        fun newInstance(context: Context, youtubeUrl: String) {
            val intent = Intent(context, YoutubePlayerActivity::class.java).apply {
                putExtra(EXTRA_URL, youtubeUrl)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemUi()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_youtube_player)
        setupPlayer()
        setupPipIcon()
    }

    private fun setupPipIcon() {
        binding.apply {
            layout.removeView(pipIcon)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                youtubePlayerView.getPlayerUiController().addView(pipIcon)
                pipIcon.setOnClickListener { enterPip() }
            }
        }
    }

    private fun setupPlayer() {
        binding.youtubePlayerView.apply {
            lifecycle.addObserver(this)
            getYouTubePlayerWhenReady(
                object : YouTubePlayerCallback {
                    override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(
                            url.extractVideoIdFromUrl() ?: "",
                            url.extractStartTimeFromUrl().toFloat()
                        )
                    }
                },
            )
        }
    }

    override fun onUserLeaveHint() {
        enterPip()
    }

    private fun enterPip() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            enterPictureInPictureMode()
        }
    }

    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration?
    ) {
        if (isInPictureInPictureMode) {
            binding.pipIcon.isVisible = false
        } else {
            binding.pipIcon.isVisible = true
            mBackstackLost = true
            hideSystemUi()
        }
    }

    override fun finish() {
        if (mBackstackLost) {
            navToLauncherTask(this)
        } else {
            super.finish()
        }
    }

    private fun navToLauncherTask(appContext: Context) {
        val activityManager = (appContext.getSystemService(ACTIVITY_SERVICE) as ActivityManager)
        val appTasks = activityManager.appTasks
        for (task in appTasks) {
            val baseIntent = task.taskInfo.baseIntent
            val categories = baseIntent.categories
            if (categories != null && categories.contains(Intent.CATEGORY_LAUNCHER)) {
                task.moveToFront()
                return
            }
        }
    }
}