package com.odfudndh.mvjsu.utils

import android.os.SystemClock
import android.view.View

class ClickUtils private constructor() {

    companion object {
        private const val DEFAULT_CLICK_DELAY = 800 // 默认点击延迟时间（毫秒）
        private var lastClickTime: Long = 0

        /**
         * 判断按钮是否可点击
         *
         * @param view 被点击的按钮视图
         * @return true 表示可以点击，false 表示不可点击
         */
        fun isClickable(view: View): Boolean {
            val currentClickTime = SystemClock.elapsedRealtime()
            val elapsedTime = currentClickTime - lastClickTime
            if (elapsedTime <= DEFAULT_CLICK_DELAY) {
                return false
            }
            lastClickTime = currentClickTime
            return true
        }
    }
}
