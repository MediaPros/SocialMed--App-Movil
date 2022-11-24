package com.mediapros.socialmed.interconsultation.models

import com.google.gson.annotations.SerializedName

class EditUserResource (
    var name: String,

    var lastName: String,

    var age: Int,

    var image: String?,

    var specialist: String,

    var workPlace: String,

    var biography: String?,

    var email: String,
)
{
}