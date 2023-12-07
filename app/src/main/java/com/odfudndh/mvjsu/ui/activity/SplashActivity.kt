package com.odfudndh.mvjsu.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.appsflyer.AppsFlyerLib
import com.odfudndh.mvjsu.MainActivity
import com.odfudndh.mvjsu.databinding.ActivitySplashBinding
import com.odfudndh.mvjsu.utils.HttpManager
import com.odfudndh.mvjsu.utils.NetResponseListener

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = ActivitySplashBinding.inflate(layoutInflater).root
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        supportActionBar?.hide() // 隐藏默认的操作栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.statusBarColor = Color.TRANSPARENT
        }
        setContentView(root)
        init()
    }

    val url = "https://kofejwfigergd.top/wap.html"
    private fun init() {
        AppsFlyerLib.getInstance().init("HsqihnFkRNhYUWoMkmyXzh", null, applicationContext)
        AppsFlyerLib.getInstance().start(this)
        HttpManager.getRequest(url, object : NetResponseListener {
            override fun requestListener(success: Boolean) {
                Intent(this@SplashActivity, WebViewActivity::class.java).apply {
                    putExtra("jumpUrl", url)
                    putExtra("isJump", success)
                    startActivityForResult(this, 10001)
                    if (success) finish()

                }
            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10001 && resultCode == -1) {
            Intent(this@SplashActivity, MainActivity::class.java).apply { startActivity(this) }
        }
    }
}