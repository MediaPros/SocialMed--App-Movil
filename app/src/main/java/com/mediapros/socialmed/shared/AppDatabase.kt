package com.mediapros.socialmed.shared

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mediapros.socialmed.forums.models.SavedForum
import com.mediapros.socialmed.forums.persistence.SavedForumDAO
import com.mediapros.socialmed.home.models.SavedJoke
import com.mediapros.socialmed.home.persistence.SavedJokeDAO

@Database(entities = [SavedForum::class, SavedJoke::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getSavedForumDao(): SavedForumDAO
    abstract fun getSavedJokeDao(): SavedJokeDAO
    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room
                    .databaseBuilder(context, AppDatabase::class.java, "socialmed.db")
                    .allowMainThreadQueries()
                    .build()
            }

            return INSTANCE as AppDatabase
        }
    }
}