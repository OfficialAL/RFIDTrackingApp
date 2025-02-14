package com.example.rfidtrackingapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Tag::class], version = 1)
abstract class TagDatabase : RoomDatabase() {
    abstract fun tagDao(): TagDao
}
