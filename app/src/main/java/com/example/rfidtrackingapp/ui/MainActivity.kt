package com.example.rfidtrackingapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rfidtrackingapp.service.RfidReaderService

class MainActivity : AppCompatActivity() {
    // ...existing code...

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ...existing code...

        // Start RFID reader service
        val intent = Intent(this, RfidReaderService::class.java)
        startService(intent)

        // Start NFC reader activity
        val nfcIntent = Intent(this, NfcReaderActivity::class.java)
        startActivity(nfcIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop RFID reader service
        val intent = Intent(this, RfidReaderService::class.java)
        stopService(intent)
    }
}
