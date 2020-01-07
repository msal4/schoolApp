package com.smart.resources.schools_app.features.pdfViewer

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.ActivityPdfWebViewBinding


class PdfWebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPdfWebViewBinding




    companion object Factory{

        fun newInstance(activity: Context,attachment:String){
            val intent= Intent(activity, PdfWebViewActivity::class.java)

            intent.putExtra("attach",attachment)

            activity.startActivity(intent)

        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_pdf_web_view)


        binding.wb.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.wb.visibility=View.GONE
                binding.progressIndicator.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                view.visibility =View.VISIBLE
                binding.progressIndicator.visibility = View.GONE
                binding.wb.visibility=View.VISIBLE
            }

        }
        binding.wb.settings.javaScriptEnabled = true

        val settings = binding.wb.settings
        settings.domStorageEnabled = true

        binding.wb.loadUrl(intent.getStringExtra("attach"))


    }
}
