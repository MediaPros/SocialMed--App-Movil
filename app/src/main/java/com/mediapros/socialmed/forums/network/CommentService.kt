package com.mediapros.socialmed.forums.network

import com.mediapros.socialmed.forums.models.Comment
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CommentService {
    @GET("api/v1/forums/{forumId}/comments")
    fun getCommentsByForumId(@Header("Authorization") token: String, @Path("forumId") forumId: Int): Call<List<Comment>>
}