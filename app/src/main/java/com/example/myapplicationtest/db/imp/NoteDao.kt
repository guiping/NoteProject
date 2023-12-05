package com.example.myapplicationtest.db.imp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplicationtest.entity.NoteEntity

@Dao
interface NoteDao {
    //增删改查
    @Insert
      fun addNewNote(vararg noteEntity:NoteEntity)
      @Query("SELECT * FROM tab_note WHERE isDeleted = 0  ")
     fun getAllNoteData():List<out NoteEntity>

    @Query("SELECT * FROM tab_note WHERE isDeleted = 1 ")
    fun getDeletedNoteData():List<out NoteEntity>


    @Query("UPDATE tab_note SET isDeleted = 1  WHERE currentTimeMillis = :currentTimeMillis")
    fun deletedNoteData(currentTimeMillis: Long): Int

    @Query("UPDATE tab_note SET isDeleted = 0  WHERE currentTimeMillis = :currentTimeMillis")
    fun restoredNoteData(currentTimeMillis: Long):Int


    @Query("DELETE FROM tab_note WHERE currentTimeMillis = :currentTimeMillis")
    fun clearNoteData(currentTimeMillis: String?):Int

    @Query("DELETE FROM tab_note WHERE isDeleted = 1")
    fun clearAllNoteData():Int


}