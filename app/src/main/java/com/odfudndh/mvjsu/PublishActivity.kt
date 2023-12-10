package com.odfudndh.mvjsu

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.odfudndh.mvjsu.databinding.ActivityPublishBinding
import com.odfudndh.mvjsu.db.DbDatabase
import com.odfudndh.mvjsu.db.imp.NoteDao
import com.odfudndh.mvjsu.entity.NoteEntity
import com.odfudndh.mvjsu.utils.ClickUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PublishActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPublishBinding
    private var isZhengXu = false
    var dbNoteDao: NoteDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 隐藏导航栏
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_FULLSCREEN
                )
        // 在活动的onCreate方法中
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        supportActionBar?.hide() // 隐藏默认的操作栏

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val window = window
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
//                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//            window.statusBarColor = Color.TRANSPARENT
//        }
        dbNoteDao = DbDatabase.buildDatabase(NoteApplication.appContext).noteDao()
        binding = ActivityPublishBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val titleEditText: EditText = binding.titleEditText
        val contentEditText: EditText = binding.contentEditText
        val btnPub: TextView = binding.btnPub
        val bundle = intent.extras
        bundle?.apply {
            val note = getString("note")
            binding.contentEditText.setText(note)
        }
        binding.btnPub.setOnClickListener {
            if (ClickUtils.isClickable(it)) {
                val title = titleEditText.text.toString()
                var content = contentEditText.text.toString()
//                if (title.isNullOrBlank()) {
//                    Toast.makeText(PublishActivity@ this, "请输入日记标题", Toast.LENGTH_SHORT)
//                        .show()
//                    return@setOnClickListener
//                }
                if (content.isNullOrBlank()) {
                    Toast.makeText(PublishActivity@ this, "请输入日记内容", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                val noteEntity =
                    NoteEntity(0, "", content, System.currentTimeMillis().toString(), 0)

                GlobalScope.launch(Dispatchers.IO) { dbNoteDao?.addNewNote(noteEntity) }
                Toast.makeText(this@PublishActivity, "发布成功", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

}