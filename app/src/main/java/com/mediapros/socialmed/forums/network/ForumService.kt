package com.mediapros.socialmed.forums.network

import com.mediapros.socialmed.forums.models.Forum
import com.mediapros.socialmed.forums.models.SaveForumResource
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ForumService {
    @GET("api/v1/forums")
    fun getAllForums(@Header("Authorization") token: String): Call<List<Forum>>

    @POST("api/v1/forums")
    fun saveForum(@Header("Authorization") token: String, @Body forumResource: SaveForumResource): Call<Forum>

    @DELETE("api/v1/forums/{id}")
    fun deleteForum(@Header("Authorization") token: String, @Path("id") id: Int): Call<Forum>
}