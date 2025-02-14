package com.example.rfidtrackingapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TagDao {
    @Insert
    suspend fun insert(tag: Tag)

    @Query("SELECT * FROM tag")
    suspend fun getAllTags(): List<Tag>
}
