package com.example.myapplicationtest.entity

import android.text.Editable

class Note(title: Editable, content: Editable, currentTimeMillis: Long, b: Boolean) {
    var title: String? = ""
    var content: String? = ""
    var createDate: String? = ""
    var isDeleted: Boolean? = false
}