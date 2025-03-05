package com.example.rfidtrackingapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rfidtrackingapp.data.Tag
import com.example.rfidtrackingapp.data.TagDatabase
import kotlinx.coroutines.launch

class TagListActivity : AppCompatActivity() {

    private lateinit var database: TagDatabase
    private lateinit var tagAdapter: TagAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tag_list)

        database = // Initialize your Room database here

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        tagAdapter = TagAdapter { tag -> onTagClicked(tag) }
        recyclerView.adapter = tagAdapter

        loadTags()
    }

    private fun loadTags() {
        lifecycleScope.launch {
            val tags = database.tagDao().getAllTags()
            tagAdapter.submitList(tags)
        }
    }

    private fun onTagClicked(tag: Tag) {
        // Handle tag click, e.g., show details or edit options
        val intent = Intent(this, TagDetailActivity::class.java)
        intent.putExtra("TAG_ID", tag.id)
        startActivity(intent)
    }

    private fun addTag(tag: Tag) {
        lifecycleScope.launch {
            database.tagDao().insert(tag)
            loadTags()
        }
    }

    private fun removeTag(tag: Tag) {
        lifecycleScope.launch {
            database.tagDao().delete(tag)
            loadTags()
        }
    }
}
