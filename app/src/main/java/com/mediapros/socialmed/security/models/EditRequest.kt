package com.mediapros.socialmed.security.models

import com.google.gson.annotations.SerializedName

class EditRequest (
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
{
//https://c8.alamy.com/compes/2gfeb69/bonita-ilustracion-de-vector-medico-aislada-sobre-fondo-blanco-ilustracion-de-dibujos-animados-del-medico-2gfeb69.jpg
}