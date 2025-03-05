package com.example.rfidtrackingapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TagDao {
    @Insert
    suspend fun insert(tag: Tag)

    @Delete
    suspend fun delete(tag: Tag)

    @Update
    suspend fun update(tag: Tag)

    @Query("SELECT * FROM tag")
    suspend fun getAllTags(): List<Tag>

    @Query("SELECT * FROM tag WHERE id = :tagId")
    suspend fun getTagById(tagId: String): Tag?
}
