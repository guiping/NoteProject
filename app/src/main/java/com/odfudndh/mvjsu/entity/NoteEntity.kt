package com.odfudndh.mvjsu.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tab_note")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id_key: Int,
    var title: String?,
    var content: String?,
    var currentTimeMillis: String?,
    var isDeleted: Int
) : Serializable