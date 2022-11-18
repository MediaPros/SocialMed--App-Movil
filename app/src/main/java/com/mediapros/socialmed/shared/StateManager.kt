package com.mediapros.socialmed.shared

import com.mediapros.socialmed.forums.models.Forum
import com.mediapros.socialmed.forums.models.SavedForum
import com.mediapros.socialmed.security.models.User
import java.text.SimpleDateFormat
import java.util.*


object StateManager {
    lateinit var authToken: String
    var loggedUserId: Int = -1
    lateinit var selectedForum: Forum
    lateinit var selectedSavedForum: SavedForum
    lateinit var selectedDoctor: User

    fun getJSDate(date: Date): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        return formatter.format(date)
    }
}