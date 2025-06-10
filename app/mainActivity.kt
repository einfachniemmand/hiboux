package com.benn.hiboux
import android.graphics.Color
import androidx.core.view.WindowCompat
import android.widget.FrameLayout
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.WHITE
        WindowCompat.getInsetsController(window, window.decorView)?.isAppearanceLightStatusBars = true
        webView = WebView(this).apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.mediaPlaybackRequiresUserGesture = false
            webViewClient = WebViewClient()
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
