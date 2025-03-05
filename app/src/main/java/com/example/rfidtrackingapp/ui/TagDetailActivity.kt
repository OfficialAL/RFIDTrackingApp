package com.example.rfidtrackingapp.ui

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.rfidtrackingapp.data.Tag
import com.example.rfidtrackingapp.data.TagDatabase
import kotlinx.coroutines.launch

class TagDetailActivity : AppCompatActivity() {

    private lateinit var database: TagDatabase
    private lateinit var tag: Tag

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tag_detail)

        database = // Initialize your Room database here

        val tagId = intent.getStringExtra("TAG_ID")
        if (tagId != null) {
            loadTag(tagId)
        } else {
            finish()
        }

        findViewById<Button>(R.id.lockButton).setOnClickListener {
            lockTag()
        }

        findViewById<Button>(R.id.unlockButton).setOnClickListener {
            unlockTag()
        }
    }

    private fun loadTag(tagId: String) {
        lifecycleScope.launch {
            tag = database.tagDao().getTagById(tagId) ?: return@launch
            findViewById<TextView>(R.id.tagName).text = tag.name
            findViewById<TextView>(R.id.tagStatus).text = if (tag.isLocked) "Locked" else "Unlocked"
        }
    }

    private fun lockTag() {
        lifecycleScope.launch {
            tag = tag.copy(isLocked = true)
            database.tagDao().update(tag)
            findViewById<TextView>(R.id.tagStatus).text = "Locked"
        }
    }

    private fun unlockTag() {
        lifecycleScope.launch {
            tag = tag.copy(isLocked = false)
            database.tagDao().update(tag)
            findViewById<TextView>(R.id.tagStatus).text = "Unlocked"
        }
    }
}
