package com.mediapros.socialmed.home.models

import com.google.gson.annotations.SerializedName


data class Joke(
    @SerializedName("id")
    var jokeId: String,

    @SerializedName("joke")
    var text: String
)