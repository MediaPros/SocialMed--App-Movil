package com.mediapros.socialmed.interconsultation.controller.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.mediapros.socialmed.R
import com.mediapros.socialmed.interconsultation.models.Recommendation
import com.mediapros.socialmed.interconsultation.models.SaveRecommendationResource
import com.mediapros.socialmed.interconsultation.network.RecommendationService
import com.mediapros.socialmed.security.controller.activities.EditLoginActivity
import com.mediapros.socialmed.security.controller.activities.EditUserProfileActivity
import com.mediapros.socialmed.security.models.User
import com.mediapros.socialmed.security.network.UserService
import com.mediapros.socialmed.shared.RetrofitBuilder
import com.mediapros.socialmed.shared.StateManager
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import org.apache.commons.validator.routines.UrlValidator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserProfileActivity : AppCompatActivity() {
    var user = StateManager.selectedDoctor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        loadUserData()
        if (user.id != StateManager.loggedUserId) {
            val btRecommendDoctor = findViewById<Button>(R.id.btRecommendDoctor)
            btRecommendDoctor.visibility = View.VISIBLE
            btRecommendDoctor.setOnClickListener {
                recommendDoctor()
            }
        }else{
            val btEditProfile = findViewById<Button>(R.id.btEditarProfile)
            btEditProfile.visibility = View.VISIBLE
            btEditProfile.setOnClickListener {
                editProfile()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        user = StateManager.selectedDoctor
        loadUserData()
    }

    private fun editProfile(){
        val intent = Intent(this, EditLoginActivity::class.java)
        startActivity(intent)
    }

    private fun recommendDoctor() {
        val token = StateManager.authToken
        val retrofit = RetrofitBuilder.build()
        val recommendationService = retrofit.create(RecommendationService::class.java)
        val request = recommendationService.createRecommendation(token, SaveRecommendationResource(StateManager.loggedUserId, user.id))
        request.enqueue(object : Callback<Recommendation> {
            override fun onResponse(
                call: Call<Recommendation>,
                response: Response<Recommendation>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(this@UserProfileActivity, "Se recomendó al doctor.", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else
                    Toast.makeText(this@UserProfileActivity, "Error al recomendar doctor: ${response.message()}.", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Recommendation>, t: Throwable) {
                Toast.makeText(this@UserProfileActivity, "Error al recomendar doctor: ${t.message}.", Toast.LENGTH_SHORT).show()
            }

        })
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
        val tvProfileBiography = findViewById<TextView>(R.id.tvProfileBiography)
        val picBuilder = Picasso.Builder(this)
        picBuilder.downloader(OkHttp3Downloader(this))

        val urlValidator = UrlValidator()
        if (urlValidator.isValid(user.image)) {
            try {
                picBuilder.build().load(user.image).into(ivProfilePic)
            }
            catch (e: Exception) {
                println("Imagen no existe.")
            }
        }
        else println("URL inválida.")

        tvProfileName.text = user.name
        tvProfileLastname.text = user.lastName
        tvProfileAge.text = "Edad: ${user.age}"
        tvProfileSpecialist.text = "Especialidad: ${user.specialist}"
        tvProfileWorkplace.text = "Trabaja en: ${user.workPlace}"
        tvProfileRec.text = "Recomendaciones: ${user.recommendation}"
        tvProfileEmail.text = "Correo: ${user.email}"
        tvProfileBiography.text = "Biografía: ${user.biography}"
    }
}