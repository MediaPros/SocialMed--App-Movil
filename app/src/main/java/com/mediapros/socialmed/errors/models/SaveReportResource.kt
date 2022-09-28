package com.mediapros.socialmed.errors.models

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.util.*

class SaveReportResource(
        @SerializedName("title")
        var title: String,
        @SerializedName("content")
        var content: String,
        @SerializedName("date")
        var date: String,
        @SerializedName("userId")
        var userId: Int
        )