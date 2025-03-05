package com.example.rfidtrackingapp.ui

import android.app.Activity
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.rfidtrackingapp.data.Tag
import com.example.rfidtrackingapp.data.TagDatabase
import kotlinx.coroutines.launch

class NfcReaderActivity : Activity() {

    private var nfcAdapter: NfcAdapter? = null
    private lateinit var database: TagDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the content view if you have a layout for this activity
        // setContentView(R.layout.activity_nfc_reader)

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC is not available on this device.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        database = // Initialize your Room database here
    }

    override fun onResume() {
        super.onResume()
        val intent = Intent(this, javaClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val nfcIntentFilter = arrayOf(IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED))
        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, nfcIntentFilter, null)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (NfcAdapter.ACTION_TAG_DISCOVERED == intent.action) {
            val tag: Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
            tag?.let {
                val tagId = it.id.joinToString("") { byte -> "%02x".format(byte) }
                Toast.makeText(this, "NFC Tag ID: $tagId", Toast.LENGTH_SHORT).show()
                handleTag(tagId)
            }
        }
    }

    private fun handleTag(tagId: String) {
        lifecycleScope.launch {
            val tag = database.tagDao().getTagById(tagId)
            if (tag != null) {
                if (tag.isLocked) {
                    // Handle locked tag
                    Toast.makeText(this@NfcReaderActivity, "Tag is locked", Toast.LENGTH_SHORT).show()
                } else {
                    // Handle unlocked tag
                    Toast.makeText(this@NfcReaderActivity, "Tag is unlocked", Toast.LENGTH_SHORT).show()
                }
            } else {
                addTagToDatabase(tagId)
            }
        }
    }

    private fun addTagToDatabase(tagId: String) {
        lifecycleScope.launch {
            val newTag = Tag(
                id = tagId,
                name = "New Tag",
                isLocked = false
            )
            database.tagDao().insert(newTag)
            // Optionally, navigate to TagListActivity to show the updated list
            val intent = Intent(this@NfcReaderActivity, TagListActivity::class.java)
            startActivity(intent)
        }
    }
}
