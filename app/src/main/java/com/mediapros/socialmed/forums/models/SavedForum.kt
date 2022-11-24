package com.mediapros.socialmed.forums.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SavedForum (
    @PrimaryKey(autoGenerate = true)
    var id: Int?,

    @ColumnInfo
    var forumId: Int,

    @ColumnInfo
    var title: String,

    @ColumnInfo
    var author: String,

    @ColumnInfo
    var content: String,

    @ColumnInfo
    var date: String
        )