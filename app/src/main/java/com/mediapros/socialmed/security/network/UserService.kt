package com.mediapros.socialmed.security.network

import com.mediapros.socialmed.security.models.AuthenticateRequest
import com.mediapros.socialmed.security.models.AuthenticateResponse
import com.mediapros.socialmed.security.models.RegisterRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("api/v1/users/sign-in")
    fun signIn(@Body request: AuthenticateRequest): Call<AuthenticateResponse>
    @POST("api/v1/users/sign-up")
    fun signUp(@Body request: RegisterRequest): Call<String>
}