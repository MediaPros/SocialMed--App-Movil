package com.mediapros.socialmed.home.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SavedJoke(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,

    @ColumnInfo
    var jokeId: String,

    @ColumnInfo
    var text: String
)