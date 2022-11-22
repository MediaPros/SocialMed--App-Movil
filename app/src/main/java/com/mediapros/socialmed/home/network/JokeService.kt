package com.mediapros.socialmed.home.network

import com.mediapros.socialmed.home.models.Joke
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface JokeService {
    @Headers("Accept: application/json")
    @GET("/")
    fun getJoke(): Call<Joke>
}