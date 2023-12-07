package com.odfudndh.mvjsu.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import com.odfudndh.mvjsu.databinding.ActivityWebBinding
import com.odfudndh.mvjsu.utils.HttpManager
import com.odfudndh.mvjsu.utils.WebChromeClient
import org.json.JSONObject

class WebViewActivity : AppCompatActivity() {
    val binding: ActivityWebBinding by lazy {
        ActivityWebBinding.inflate(layoutInflater)
    }
    var webView: WebView? = null
    var jump: Boolean = false
    var url: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        supportActionBar?.hide() // 隐藏默认的操作栏

        setContentView(binding.root)
        val bundle = intent.extras
        if (bundle != null) {
            url = bundle.getString("jumpUrl")
            jump = bundle.getBoolean("isJump")
        }
        if (url.isNullOrEmpty()) {
            //跳转进入首页
            finishWeb()
        }


        binding.apply {
            wWebRoot.visibility = if (jump) View.GONE else View.VISIBLE
            webView = if (jump) fWeb else wWeb
            webView?.visibility = View.VISIBLE
            imgClose.setOnClickListener {
                finishWeb()
            }
            initWebView(webView!!)
            url?.apply {
                webView?.loadUrl(this)
            }

        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView(webView: WebView) {
        webView.settings.useWideViewPort = true
        webView.settings.defaultTextEncodingName = "utf-8"
        webView.settings.savePassword = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.javaScriptEnabled = true
        webView.settings.allowFileAccess = true
        webView.settings.domStorageEnabled = true
        webView.settings.setSupportMultipleWindows(true)
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.settings.setSupportMultipleWindows(true)
        webView.settings.allowFileAccess = true
        webView.isHorizontalScrollBarEnabled = false
        webView.isVerticalScrollBarEnabled = false
        webView.addJavascriptInterface(object : Any() {
            @JavascriptInterface
            fun postMessage(tag: String, value: String) {
                Log.e("pLog", "postMessage  jsBridge    -- tag = $tag  value = $value")
                when (tag) {
                    "openWindow" -> {
                        try {
                            val jsonObject = JSONObject(value)
                            val url1 = jsonObject.optString("url")
                            HttpManager.openWebView(this@WebViewActivity, url1)
                        } catch (e: Exception) {

                        }
                    }

                    "closeWindow" -> {
                        this@WebViewActivity.finish()
                    }

                    else -> {
                        if (HttpManager.needSendFlyerEvent(tag)) {
                            HttpManager.logEvent(this@WebViewActivity, tag, value)
                        }
                    }
                }
            }
        }, "jsBridge")
        webView.webChromeClient = WebChromeClient(this, webView)
        webView.setDownloadListener { str, str2, str3, str4, j2 ->

            HttpManager.openAndroid(this@WebViewActivity, str)
            finish()
        }
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.e("pLog", "onPageFinished---- $url")
            }

            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                Log.e("pLog", "shouldOverrideUrlLoading---- ${request.url.toString()}")
                val loadUrl = request?.url.toString()
                if (loadUrl != null && loadUrl.startsWith("tg:") || loadUrl.startsWith("fb://")) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                    return true
                }
                if (loadUrl.startsWith("file://")) {
                    // 如果 URL 协议为 file://，则启动 APK 安装程序
                    HttpManager.openAndroid(this@WebViewActivity, loadUrl)
                    finish()
                    return true
                }
                return false
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return onKeyDown()
    }

    private fun finishWeb() {
        setResult(-2)
        finish()
    }

    fun onKeyDown(): Boolean {
        return if (webView == null) {
            finish()
            true
        } else if (webView!!.canGoBack()) {
            this.webView?.goBack()
            true
        } else if (webView!!.size > 0) {
//            (findViewById<View>() as FrameLayout).removeView(webView)
//            val aab2: WebView = this.f3845aaz.aab()
//            this.webView = aab2
//            if (aab2 != null) {
//                aab2.visibility = 0
//                this.f3844aay.aaf(this.f3841aav)
//                return true
//            }
            finish()
            true
        } else {
            finish()
            true
        }
    }

}