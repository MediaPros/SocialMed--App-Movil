package com.mediapros.socialmed.forums.network

import com.mediapros.socialmed.forums.models.Comment
import com.mediapros.socialmed.forums.models.SaveCommentResource
import retrofit2.Call
import retrofit2.http.*

interface CommentService {
    @GET("api/v1/forums/{forumId}/comments")
    fun getCommentsByForumId(@Header("Authorization") token: String, @Path("forumId") forumId: Int): Call<List<Comment>>

    @POST("api/v1/comments")
    fun createComment(@Header("Authorization") token: String, @Body resource: SaveCommentResource): Call<Comment>

    @DELETE("api/v1/comments/{id}")
    fun deleteComment(@Header("Authorization") token: String, @Path("id") id: Int): Call<Comment>
}