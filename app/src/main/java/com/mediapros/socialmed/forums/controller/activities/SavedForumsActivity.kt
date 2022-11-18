package com.mediapros.socialmed.forums.controller.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mediapros.socialmed.R
import com.mediapros.socialmed.forums.adapter.SavedForumAdapter
import com.mediapros.socialmed.forums.models.SavedForum
import com.mediapros.socialmed.shared.AppDatabase
import com.mediapros.socialmed.shared.OnItemClickListener
import com.mediapros.socialmed.shared.StateManager

class SavedForumsActivity : AppCompatActivity(), OnItemClickListener<SavedForum> {
    lateinit var recyclerView: RecyclerView
    lateinit var forums: List<SavedForum>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_forums)
        recyclerView = findViewById(R.id.rvSavedForums)
        loadSavedForums()
    }

    override fun onResume() {
        super.onResume()
        loadSavedForums()
    }

    private fun loadSavedForums() {
        forums = AppDatabase.getInstance(this).getSavedForumDao().getAll()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SavedForumAdapter(forums, this, this)

    }

    private fun goToSavedForumDetailsActivity(forum: SavedForum) {
        println("Entró a detalles")
        println(forum)
        StateManager.selectedSavedForum = forum
        val intent = Intent(this, SavedForumDetailsActivity::class.java)
        startActivity(intent)
    }

    override fun onItemClicked(value: SavedForum) {
        goToSavedForumDetailsActivity(value)
    }
}