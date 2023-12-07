package com.odfudndh.mvjsu.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.appsflyer.AFInAppEventParameterName
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.odfudndh.mvjsu.ui.activity.WebViewActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object HttpManager {
    fun getRequest(url: String, responseListener: NetResponseListener) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val openConnection = URL(url).openConnection()
                val httpUrlCon = openConnection as HttpURLConnection
                httpUrlCon.instanceFollowRedirects = false
                httpUrlCon.readTimeout = 30000
                httpUrlCon.connectTimeout = 30000
                httpUrlCon.connect()
                Log.e("tag", "--- ${httpUrlCon.responseCode}")
                responseListener.requestListener(httpUrlCon.responseCode == 302)
            } catch (e: Exception) {
                e.printStackTrace()
                responseListener.requestListener(false)
            }
        }
    }

    val EVENTS1 = arrayOf(
        "login",
        "firstrecharger",
        "login",
        "registerClick",
        "logout",
        "logout22222",
        "registerClick",
        "rechargeClick", "register",
        "recharge", "withdrawClick", "withdrawClick111","withdrawOrderSuccess", "firstrecharge"
    )
    fun needSendFlyerEvent(event: String): Boolean {
        return listOf(*EVENTS1).contains(event)
    }


    fun openWebView(activity: Activity, url: String) {
        Log.e("pLog", "openWebView ==== $url")
        val intent = Intent(activity, WebViewActivity::class.java)
        intent.putExtra("jumpUrl", url)
        intent.putExtra("isJump", true)
        activity.startActivityForResult(intent, 1002)
    }

    fun closeWebView(activity: Activity) {
        Log.e("pLog", "closeWebView --=====")
        activity.finish()
    }


    fun openAndroid(activity: Activity, url: String) {
        Log.e("pLog", "openAndroid --=====$url")
        try {
            Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                val chooser = Intent.createChooser(this, "Choose a browser")
                activity.startActivity(chooser)
            }
        } catch (e: Exception) {
            Log.e("xxxTag", "openSystemBrowser failure $e")
        }
    }


    fun eventTracker(activity: Activity, eventType: String, eventValues: String) {
        Log.e("pLog", "eventTracker --=====$eventType ===  eventValues = $eventValues")
        if (eventValues.isNotEmpty()) {
            val gson = Gson()
            val type = object : TypeToken<Map<String?, String>>() {}.type
            val map = gson.fromJson<Map<String, String>>(eventValues, type)
            AppsFlyerLib.getInstance().logEvent(activity, eventType, map)
        }
    }

    fun logEvent(activity: Activity, tag: String, value: String) {
        val gson = Gson()
        val type = object : TypeToken<Map<String?, Any?>?>() {}.type
        var eventValues: MutableMap<String?, Any?>

        eventValues = HashMap()
        eventValues[AFInAppEventParameterName.CONTENT_ID] = tag
        eventValues[AFInAppEventParameterName.CONTENT_TYPE] = tag
        eventValues[AFInAppEventParameterName.CONTENT] = value
        try {
            val jsonObject = JSONObject(value)
            val amount = jsonObject.optString("amount")
            eventValues[AFInAppEventParameterName.REVENUE] = amount
            eventValues[AFInAppEventParameterName.CURRENCY] = "PHP"
            Log.e("pLog", "logEvent: amount = $amount")
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        AppsFlyerLib.getInstance()
            .logEvent(activity, tag, eventValues, object : AppsFlyerRequestListener {
                override fun onSuccess() {
                    Log.i("pLog", "AppsFlyerLib onSuccess")
                }

                override fun onError(i: Int, s: String) {
                    Log.i("pLog", "AppsFlyerLib onError")
                }
            })

    }
}