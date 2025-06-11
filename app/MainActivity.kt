package com.benn.hiboux

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.view.View
import android.webkit.*
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Color.WHITE
        WindowCompat.getInsetsController(window, window.decorView)?.isAppearanceLightStatusBars = true

        webView = WebView(this).apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.setSupportMultipleWindows(true)

            webViewClient = WebViewClient()
            webChromeClient = object : WebChromeClient() {
                override fun onCreateWindow(
                    view: WebView,
                    isDialog: Boolean,
                    isUserGesture: Boolean,
                    resultMsg: Message
                ): Boolean {
                    val transport = resultMsg.obj as WebView.WebViewTransport
                    val tempWebView = WebView(view.context)

                    tempWebView.webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            startActivity(intent)
                            return true
                        }
                    }

                    transport.webView = tempWebView
                    resultMsg.sendToTarget()
                    return true
                }
            }

            loadUrl("file:///android_asset/index.html")
            fitsSystemWindows = true
        }

        val layout = FrameLayout(this).apply {
            fitsSystemWindows = true
            setPadding(0, getStatusBarHeight(), 0, 0)
            addView(webView)
        }

        setContentView(layout)
    }

    override fun onBackPressed() {
        webView.evaluateJavascript("handleBackArrow()", null)
    }

    private fun getStatusBarHeight(): Int {
        val resId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resId > 0) resources.getDimensionPixelSize(resId) else 0
    }
}
