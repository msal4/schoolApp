package com.smart.resources.schools_app.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.ActivityPdfWebViewBinding
import kotlinx.android.synthetic.main.activity_pdf_web_view.*


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
        binding.wb.settings.javaScriptEnabled=true
        binding.wb.webViewClient = WebViewClient()
        binding.wb.loadUrl(intent.getStringExtra("attach"))
        wb.settings.builtInZoomControls=true

    }
}
