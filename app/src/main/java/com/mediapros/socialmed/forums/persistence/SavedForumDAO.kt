package com.mediapros.socialmed.forums.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mediapros.socialmed.forums.models.Forum
import com.mediapros.socialmed.forums.models.SavedForum

@Dao
interface SavedForumDAO {
    @Query("SELECT * FROM SavedForum")
    fun getAll(): List<SavedForum>

    @Insert
    fun insertForum(vararg forum: SavedForum)

    @Delete
    fun deleteForum(vararg forum: SavedForum)

    @Query("SELECT * FROM SavedForum WHERE forumId = :forumId AND title = :title")
    fun getByForumIdAndTitle(forumId: Int, title: String): List<SavedForum>
}