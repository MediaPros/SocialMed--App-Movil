package com.mediapros.socialmed.shared

import android.content.Context
import android.widget.TextView
import android.widget.Toast
import com.mediapros.socialmed.forums.models.Forum
import com.mediapros.socialmed.security.models.User
import com.mediapros.socialmed.security.network.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


object StateManager {
    lateinit var authToken: String
    var userId: Int = -1
    lateinit var selectedForum: Forum

    fun getJSDate(date: Date): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        return formatter.format(date)
    }
}