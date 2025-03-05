package com.example.rfidtrackingapp.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.lifecycle.lifecycleScope
import com.example.rfidtrackingapp.data.TagDatabase
import com.example.rfidtrackingapp.data.VirtualDevice
import kotlinx.coroutines.launch

class RfidReaderService : Service() {

    private lateinit var database: TagDatabase

    override fun onCreate() {
        super.onCreate()
        database = // Initialize your Room database here
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Initialize RFID reader and start reading tags
        // Simulate reading an RFID tag
        val rfidTagId = "sampleRfidTagId"
        linkTagWithVirtualDevice(rfidTagId)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        // Clean up RFID reader resources
    }

    private fun linkTagWithVirtualDevice(rfidTagId: String) {
        lifecycleScope.launch {
            val virtualDevice = database.virtualDeviceDao().getVirtualDeviceByTag(rfidTagId, "")
            if (virtualDevice != null) {
                // Virtual device found, start VirtualDeviceActivity
                val intent = Intent(this@RfidReaderService, VirtualDeviceActivity::class.java)
                intent.putExtra("DEVICE_ID", virtualDevice.id)
                startActivity(intent)
            } else {
                // No virtual device found, create a new one
                val newVirtualDevice = VirtualDevice(
                    id = generateUniqueId(),
                    rfidTagId = rfidTagId,
                    nfcTagId = "",
                    name = "New Virtual Device"
                )
                database.virtualDeviceDao().insert(newVirtualDevice)
                // Start VirtualDeviceActivity with the new device
                val intent = Intent(this@RfidReaderService, VirtualDeviceActivity::class.java)
                intent.putExtra("DEVICE_ID", newVirtualDevice.id)
                startActivity(intent)
            }
        }
    }

    private fun generateUniqueId(): String {
        // Generate a unique ID for the virtual device
        return java.util.UUID.randomUUID().toString()
    }
}
