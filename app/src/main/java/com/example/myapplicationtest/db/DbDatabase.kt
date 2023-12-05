package com.example.myapplicationtest.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplicationtest.db.imp.NoteDao
import com.example.myapplicationtest.entity.NoteEntity

@Database(entities = [NoteEntity::class] , version = 3,exportSchema = false)
abstract class DbDatabase:RoomDatabase() {
    abstract  fun  noteDao():NoteDao
    companion object {
         const val DB_NAME = "persnal_note.db"

        fun buildDatabase(context: Context): DbDatabase {
            return Room.databaseBuilder(context.applicationContext, DbDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}