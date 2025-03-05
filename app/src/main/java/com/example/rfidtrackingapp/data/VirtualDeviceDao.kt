package com.example.rfidtrackingapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface VirtualDeviceDao {
    @Insert
    suspend fun insert(virtualDevice: VirtualDevice)

    @Query("SELECT * FROM virtualdevice WHERE rfidTagId = :rfidTagId OR nfcTagId = :nfcTagId")
    suspend fun getVirtualDeviceByTag(rfidTagId: String, nfcTagId: String): VirtualDevice?
}
