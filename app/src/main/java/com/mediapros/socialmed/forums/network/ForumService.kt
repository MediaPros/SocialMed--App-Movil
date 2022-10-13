package com.mediapros.socialmed.forums.network

import com.mediapros.socialmed.forums.models.Forum
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ForumService {
    @GET("api/v1/forums")
    fun getAllForums(@Header("Authorization") token: String): Call<List<Forum>>
}