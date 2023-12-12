package com.odfudndh.mvjsu.utils

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.odfudndh.mvjsu.ui.activity.WebViewActivity

object StartActivityManager {
    fun openWebView(activity: Activity, url: String) {
        Log.e("pLog", "openWebView ==== $url")
        val intent = Intent(activity, WebViewActivity::class.java)
        intent.putExtra("jumpUrl", url)
        intent.putExtra("isJump", true)
        activity.startActivityForResult(intent, 1002)
    }
}