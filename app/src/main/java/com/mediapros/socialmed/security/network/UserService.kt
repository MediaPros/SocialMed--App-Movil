package com.mediapros.socialmed.security.network

import com.mediapros.socialmed.interconsultation.models.EditUserResource
import com.mediapros.socialmed.security.models.*
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @GET("api/v1/users")
    fun getAllUsers(@Header("Authorization") token: String): Call<List<User>>

    @GET("api/v1/users/{userId}")
    fun getUserById(@Header("Authorization") token: String, @Path("userId") userId: Int): Call<User>

    @POST("api/v1/users/sign-in")
    fun signIn(@Body request: AuthenticateRequest): Call<AuthenticateResponse>

    @POST("api/v1/users/sign-up")
    fun signUp(@Body request: RegisterRequest): Call<String>

    @PUT("api/v1/users/{userId}")
    fun editUser(@Header("Authorization") token: String, @Body request: EditRequest, @Path("userId") userId: Int): Call<User>
}