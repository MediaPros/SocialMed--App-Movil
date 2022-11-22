package com.mediapros.socialmed.home.controller.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mediapros.socialmed.R
import com.mediapros.socialmed.home.adapter.SavedJokeAdapter
import com.mediapros.socialmed.home.models.SavedJoke
import com.mediapros.socialmed.shared.AppDatabase
import com.mediapros.socialmed.shared.OnItemClickListener

class SavedJokesActivity : AppCompatActivity(), OnItemClickListener<SavedJoke> {
    lateinit var jokes: List<SavedJoke>
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_jokes)
        recyclerView = findViewById(R.id.rvSavedJokes)
        loadJokes()
    }

    private fun loadJokes() {
        jokes = AppDatabase.getInstance(this).getSavedJokeDao().getAll()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SavedJokeAdapter(jokes, this)
    }

    override fun onItemClicked(value: SavedJoke) {
        deleteJokeFromDb(value)
    }

    private fun deleteJokeFromDb(joke: SavedJoke) {
        AppDatabase.getInstance(this).getSavedJokeDao().deleteJoke(joke)
        Toast.makeText(this, "Chiste eliminado del dispositivo.", Toast.LENGTH_LONG).show()
        loadJokes()
    }
}