package com.example.myapplicationtest

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplicationtest.databinding.ActivityMainBinding
import com.example.myapplicationtest.databinding.ActivityPublishBinding

class PublishActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPublishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPublishBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val titleEditText: EditText = binding.titleEditText
        val contentEditText: EditText = binding.contentEditText
        val btnPub: Button = binding.btnPub
    }
}