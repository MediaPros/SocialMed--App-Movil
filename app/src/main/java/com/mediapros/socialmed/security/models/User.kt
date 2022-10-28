package com.mediapros.socialmed.security.models

import com.google.gson.annotations.SerializedName

class User (
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("lastName")
    var lastName: String,
    @SerializedName("age")
    var age: Int,
    @SerializedName("image")
    var image: String?,
    @SerializedName("email")
    var email: String,
    @SerializedName("specialist")
    var specialist: String,
    @SerializedName("recommendation")
    var recommendation: Int,
    @SerializedName("workPlace")
    var workPlace: String,
    @SerializedName("biography")
    var biography: String?
    )