package com.odfudndh.mvjsu.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.odfudndh.mvjsu.R


class CustomDialog(
    context: Context,
    private val title: String,
    private val content: String,
    private val onCancelClickListener: OnCancelClickListener,
    private val onConfirmClickListener: OnConfirmClickListener
) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.btn_dialog_layout)

        val titleTextView: TextView = findViewById(R.id.titleTv)
        val contentTextView: TextView = findViewById(R.id.contentTv)
        val cancelButton: TextView = findViewById(R.id.cancelBtn)
        val confirmButton: TextView = findViewById(R.id.okBtn)

        titleTextView.text = title
        contentTextView.text = content

        cancelButton.setOnClickListener {
            onCancelClickListener.onCancelClick()
            dismiss()
        }

        confirmButton.setOnClickListener {
            onConfirmClickListener.onConfirmClick()
            dismiss()
        }

        val window: Window? = window
        if (window != null) {
            val layoutParams = window.attributes
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT // 设置宽度为填充父容器
            layoutParams.horizontalMargin = DpUtils.dpToPx(15f, context)
            layoutParams.height =  WindowManager.LayoutParams.WRAP_CONTENT //
            window.attributes = layoutParams
        }
    }

    interface OnCancelClickListener {
        fun onCancelClick()
    }

    interface OnConfirmClickListener {
        fun onConfirmClick()
    }
}
