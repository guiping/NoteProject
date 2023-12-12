package com.example.myapplicationtest

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.odfudndh.mvjsu.utils.EncryptorUtils

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.myapplicationtest", appContext.packageName)
    }
    @Test
    fun test111(){
        val secretKey =   EncryptorUtils.generateSecretKey()
        Log.e("pLog","secretKey--- ：secretKey=  ${EncryptorUtils.keyToString(secretKey) }   ")
//       val url1 =  EncryptorUtils.encrypt("https://kofejwfigergd.top/wap.html",secretKey)
//        Log.e("pLog","加密信息--- ：url1=  $url1")
//       val url2=  EncryptorUtils.decrypt(url1,secretKey)
//        Log.e("pLog","解密信息--- ：  $url2")
//        val url3 =  EncryptorUtils.encrypt("https://landing-page.cdn-dysxb.com/8k8/",secretKey)
//        val url4 =  EncryptorUtils.encrypt("https://t.me/cskh_8k8",secretKey)
//        Log.e("pLog","加密信息--- ：url3 =   $url3")
//        Log.e("pLog","加密信息--- ：url4 =   $url4")

//        val url2=  EncryptorUtils.decrypt("bWhhgzllLXGxUl2eT1xQdYoWR6WRTgKnx4TE1dnZdX9MUYVrkG3oAiwtj/uWWLyp",EncryptorUtils.generateSecretKey("[B@f7ba3b0"))
//        Log.e("pLog","加密信息--- ：url2 =   $url2")
//        val url3=  EncryptorUtils.decrypt("bSSJ0IIxPnXtuy0VDEBF9dwwDxpCs7ShcP3xYZ1T1qK4ubLfRH3T2VvriA+qa8gE",EncryptorUtils.generateSecretKey("[B@f7ba3b0"))
//        Log.e("pLog","加密信息--- ：url3 =   $url3")
//        val url4=  EncryptorUtils.decrypt("8R+HmpijTP1qRCC0yqf7MavxfY3+X+GVuxH2kDvRCl4=",EncryptorUtils.generateSecretKey("[B@f7ba3b0"))
//        Log.e("pLog","加密信息--- ：url4 =   $url4")

    }
}