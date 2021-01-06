package com.smart.resources.schools_app.features.liveStream

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.activity.SectionActivity
import com.smart.resources.schools_app.databinding.FragmentLiveStreamBinding
import com.smart.resources.schools_app.features.users.data.repository.UserRepository
import kotlinx.coroutines.launch
import timber.log.Timber


class LiveStreamFragment : Fragment() {
    private lateinit var binding: FragmentLiveStreamBinding

    companion object {
        fun newInstance(fm: FragmentManager) {
            val fragment =
                LiveStreamFragment()
            fm.beginTransaction().apply {
                setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left,
                    R.anim.slide_in_left,
                    R.anim.slide_out_right
                )
                add(R.id.fragmentContainer, fragment)
                commit()
            }
        }
    }
    lateinit var webkitPermissionRequest : PermissionRequest
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLiveStreamBinding
            .inflate(inflater, container, false)
        WebView.setWebContentsDebuggingEnabled(true);

        lifecycleScope.launch {
            UserRepository.instance.getCurrentAccount()?.let {
                binding.webview.loadUrl("https://live.srit-school.com")
                binding.webview.settings.javaScriptEnabled = true;
                binding.webview.settings.mediaPlaybackRequiresUserGesture = false;
                binding.webview.settings.useWideViewPort = true;
                binding.webview.settings.javaScriptCanOpenWindowsAutomatically = true;
                binding.webview.settings.loadWithOverviewMode = true;
                binding.webview.settings.cacheMode = WebSettings.LOAD_NO_CACHE;
                val submitButtonId = if (it.isStudent)  "joinViewer" else "joinBroadcaster"
                binding.webview.webChromeClient = object : WebChromeClient() {
                    override fun onPermissionRequest(request: PermissionRequest) {
                        request.grant(request.resources)
                    }
                }

                binding.webview.webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        binding.webview.evaluateJavascript("""
                            document.querySelector('#name').value = '${it.username}';
                            document.querySelector('#roomNumber').value = 'ss';
                            document.querySelector('#$submitButtonId').click();
                            """) {}
                        super.onPageFinished(view, url)
                    }
                }
            }
        }
        (activity as SectionActivity).setCustomTitle(R.string.live_stream)
        return binding.root
    }

}