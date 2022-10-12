package com.mediapros.socialmed

import java.text.SimpleDateFormat
import java.util.*


object StateManager {
    lateinit var authToken: String
    var userId: Int = -1

    fun getJSDate(date: Date): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        return formatter.format(date)
    }
}