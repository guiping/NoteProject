package com.example.myapplicationtest

import android.app.Application
import android.content.Context

class NoteApplication:Application (){

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object {
        lateinit var appContext: Context
            set
    }
}
