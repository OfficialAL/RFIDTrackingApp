package com.example.rfidtrackingapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VirtualDevice(
    @PrimaryKey val id: String,
    val rfidTagId: String,
    val nfcTagId: String,
    val name: String
)
