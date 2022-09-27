package com.mediapros.socialmed.security.models

import com.google.gson.annotations.SerializedName

class RegisterRequest(
    @SerializedName("name")
    var name: String,
    @SerializedName("lastName")
    var lastName: String,
    @SerializedName("age")
    var age: Int,
    @SerializedName("image")
    var image: String?,
    @SerializedName("specialist")
    var specialist: String,
    @SerializedName("workPlace")
    var workPlace: String,
    @SerializedName("biography")
    var biography: String?,
    @SerializedName("email")
    var email: String,
    @SerializedName("password")
    var password: String
)