package com.example.rfidtrackingapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Tag::class, VirtualDevice::class], version = 2)
abstract class TagDatabase : RoomDatabase() {
    abstract fun tagDao(): TagDao
    abstract fun virtualDeviceDao(): VirtualDeviceDao
}
