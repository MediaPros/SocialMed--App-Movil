package com.mediapros.socialmed.forums.controller.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.mediapros.socialmed.R
import com.mediapros.socialmed.forums.models.SavedForum
import com.mediapros.socialmed.shared.AppDatabase
import com.mediapros.socialmed.shared.StateManager

class SavedForumDetailsActivity : AppCompatActivity() {
    lateinit var forum: SavedForum
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_forum_details)
        forum = StateManager.selectedSavedForum
        val btDeleteSForum = findViewById<ImageButton>(R.id.btDeleteSForum)
        btDeleteSForum.setOnClickListener {
            deleteForumFromDb()
        }
        loadForumDetails()
    }

    private fun deleteForumFromDb() {
        AppDatabase.getInstance(this).getSavedForumDao().deleteForum(forum)
        Toast.makeText(this, "Entrada eliminada del dispositivo.", Toast.LENGTH_LONG).show()
        finish()
    }

    private fun loadForumDetails() {
        val tvSForumDetTitle = findViewById<TextView>(R.id.tvSForumDetTitle)
        val tvSForumDetAuthor = findViewById<TextView>(R.id.tvSForumDetAuthor)
        val tvSForumDetContent = findViewById<TextView>(R.id.tvSForumDetContent)

        tvSForumDetTitle.text = forum.title
        tvSForumDetAuthor.text = forum.author
        tvSForumDetContent.text = forum.content
    }
}