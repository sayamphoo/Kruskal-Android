package com.sayamphoo.kruskals

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(WebView(this).apply {
            intent.getStringExtra("URL_WEB")?.let { loadUrl(it) }
        })
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        finish()
    }
}