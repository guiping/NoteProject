package com.example.myapplicationtest

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplicationtest.databinding.ActivityPublishBinding
import com.example.myapplicationtest.entity.Note
import com.example.myapplicationtest.utils.ClickUtils

class PublishActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPublishBinding
    private var isZhengXu = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPublishBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val titleEditText: EditText = binding.titleEditText
        val contentEditText: EditText = binding.contentEditText
        val btnPub: Button = binding.btnPub
        binding.
        btnPub.setOnClickListener {
            if (ClickUtils.isClickable(it)) {
                val title = titleEditText.text
                var content = contentEditText.text
                if(title.isNullOrBlank()){
                    Toast.makeText(PublishActivity@this,"请输入日记标题",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if(content.isNullOrBlank()){
                    Toast.makeText(PublishActivity@this,"请输入日记内容",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val note = Note(title,content,System.currentTimeMillis(),false)
                //TODO  保存笔记
            }
        }
    }

}