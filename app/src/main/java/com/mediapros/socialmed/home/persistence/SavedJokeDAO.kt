package com.mediapros.socialmed.home.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mediapros.socialmed.home.models.SavedJoke

@Dao
interface SavedJokeDAO {
    @Query("SELECT * FROM SavedJoke")
    fun getAll(): List<SavedJoke>

    @Insert
    fun insertJoke(vararg joke: SavedJoke)

    @Delete
    fun deleteJoke(vararg joke: SavedJoke)

    @Query("SELECT * FROM SavedJoke WHERE jokeId = :jokeId")
    fun getByJokeId(jokeId: String): List<SavedJoke>
}