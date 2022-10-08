package com.reyndev.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebView
import android.webkit.WebViewClient
import com.reyndev.newsapp.databinding.ActivityArticleBinding

class ArticleActivity : AppCompatActivity() {

    lateinit var binding: ActivityArticleBinding
    lateinit var web: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.getStringExtra("EXTRA_URL")

        web = binding.wvNews
        true.also { web.settings.javaScriptEnabled = it }
        web.webViewClient = WebViewClient()

        if (url != null) {
            web.loadUrl(url)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && web.canGoBack()) {
            web.goBack()
            return true
        }

        return super.onKeyDown(keyCode, event)
    }
}