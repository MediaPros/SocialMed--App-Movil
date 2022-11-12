package com.mediapros.socialmed.interconsultation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.mediapros.socialmed.R
import com.mediapros.socialmed.shared.StateManager
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso

class UserProfileActivity : AppCompatActivity() {
    val user = StateManager.selectedDoctor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        loadUserData()
    }

    private fun loadUserData() {
        val tvProfileName = findViewById<TextView>(R.id.tvProfileName)
        val tvProfileLastname = findViewById<TextView>(R.id.tvProfileLastname)
        val ivProfilePic = findViewById<ImageView>(R.id.ivProfilePic)
        val tvProfileAge = findViewById<TextView>(R.id.tvProfileAge)
        val tvProfileSpecialist = findViewById<TextView>(R.id.tvProfileSpecialist)
        val tvProfileWorkplace = findViewById<TextView>(R.id.tvProfileWorkplace)
        val tvProfileRec = findViewById<TextView>(R.id.tvProfileRec)
        val tvProfileEmail = findViewById<TextView>(R.id.tvProfileEmail)
        val picBuilder = Picasso.Builder(this)
        picBuilder.downloader(OkHttp3Downloader(this))

        tvProfileName.text = user.name
        tvProfileLastname.text = user.lastName
        picBuilder.build().load(user.image).into(ivProfilePic)
        tvProfileAge.text = "Edad: ${user.age}"
        tvProfileSpecialist.text = "Especialidad: ${user.specialist}"
        tvProfileWorkplace.text = "Trabaja en: ${user.workPlace}"
        tvProfileRec.text = "Recomendaciones: ${user.recommendation}"
        tvProfileEmail.text = "Correo: ${user.email}"
    }
}