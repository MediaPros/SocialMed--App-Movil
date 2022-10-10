package com.mediapros.socialmed.errors.models

import com.google.gson.annotations.SerializedName
import com.mediapros.socialmed.security.models.User
import java.util.*

class Report (
    @SerializedName("id")
    var id: Int,
    @SerializedName("title")
    var title: String,
    @SerializedName("content")
    var content: String,
    @SerializedName("date")
    var date: String,
    @SerializedName("user")
    var user: User
        )