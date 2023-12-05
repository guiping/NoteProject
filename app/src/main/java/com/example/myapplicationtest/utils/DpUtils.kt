package com.example.myapplicationtest.utils

import android.content.Context
import android.util.TypedValue

object DpUtils {
    /**
     * 将dp转换为px
     *
     * @param dp 需要转换的dp值
     * @param context 应用程序上下文
     * @return 转换后的px值
     */
    @JvmStatic
    fun dpToPx(dp: Float, context: Context): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        )
    }
}