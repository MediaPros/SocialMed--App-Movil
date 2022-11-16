package com.mediapros.socialmed.interconsultation.network

import com.mediapros.socialmed.interconsultation.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface RecommendationService {
    @POST("api/v1/recommendations")
    fun createRecommendation(@Header("Authorization") token: String, @Body resource: SaveRecommendationResource): Call<Recommendation>
}