package com.mediapros.socialmed.security.controller.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.mediapros.socialmed.R
import com.mediapros.socialmed.interconsultation.controller.activities.UserProfileActivity
import com.mediapros.socialmed.security.models.EditRequest
import com.mediapros.socialmed.security.models.RegisterUpdateResponse
import com.mediapros.socialmed.security.models.User
import com.mediapros.socialmed.security.network.UserService
import com.mediapros.socialmed.shared.RetrofitBuilder
import com.mediapros.socialmed.shared.StateManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditUserProfileActivity : AppCompatActivity() {
    val user = StateManager.selectedDoctor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user_profile)
        loadUserEditData()
        val btEditProfile = findViewById<Button>(R.id.btProfileEdit)
        btEditProfile.setOnClickListener {
            editProfileAction()
        }

    }

    private fun loadUserEditData(){
        val etEmailEdit = findViewById<EditText>(R.id.etEmailEdit)
        val etPasswordEdit = findViewById<EditText>(R.id.etPasswordEdit)
        val etNameEdit = findViewById<EditText>(R.id.etNameEdit)
        val etLastNameEdit = findViewById<EditText>(R.id.etLastNameEdit)
        val etAgeEdit = findViewById<EditText>(R.id.etAgeEdit)
        val etSpecialistEdit = findViewById<EditText>(R.id.etSpecialistEdit)
        val etImageEdit = findViewById<EditText>(R.id.etImageEdit)
        val etWorkplaceEdit = findViewById<EditText>(R.id.etWorkplaceEdit)
        val etBiographyEdit = findViewById<EditText>(R.id.etBiographyEdit)

        etEmailEdit.setText(user.email)
        etPasswordEdit.setText(intent.extras?.getString("password"))
        etNameEdit.setText(user.name)
        etLastNameEdit.setText(user.lastName)
        etAgeEdit.setText(user.age.toString())
        etSpecialistEdit.setText(user.specialist)
        etImageEdit.setText(user.image)
        etWorkplaceEdit.setText(user.workPlace)
        etBiographyEdit.setText(user.biography)
    }

    private fun editProfileAction(){
        val etEmailEdit = findViewById<EditText>(R.id.etEmailEdit)
        val etPasswordEdit = findViewById<EditText>(R.id.etPasswordEdit)
        val etNameEdit = findViewById<EditText>(R.id.etNameEdit)
        val etLastNameEdit = findViewById<EditText>(R.id.etLastNameEdit)
        val etAgeEdit = findViewById<EditText>(R.id.etAgeEdit)
        val etSpecialistEdit = findViewById<EditText>(R.id.etSpecialistEdit)
        val etImageEdit = findViewById<EditText>(R.id.etImageEdit)
        val etWorkplaceEdit = findViewById<EditText>(R.id.etWorkplaceEdit)
        val etBiographyEdit = findViewById<EditText>(R.id.etBiographyEdit)

        val retrofit = RetrofitBuilder.build()
        val userService = retrofit.create(UserService::class.java)


        val request = userService.editUser(StateManager.authToken, EditRequest(
            etNameEdit.text.toString(),
            etLastNameEdit.text.toString(),
            etAgeEdit.text.toString().toInt(),
            etImageEdit.text.toString(),
            etSpecialistEdit.text.toString(),
            etWorkplaceEdit.text.toString(),
            etBiographyEdit.text.toString(),
            etEmailEdit.text.toString(),
            etPasswordEdit.text.toString()
        ), StateManager.loggedUserId)

        request.enqueue(object : Callback<RegisterUpdateResponse> {
            override fun onResponse(call: Call<RegisterUpdateResponse>, response: Response<RegisterUpdateResponse>) {
                if (response.isSuccessful)
                {
                    Toast.makeText(this@EditUserProfileActivity, "Perfil editado correctamente.", Toast.LENGTH_LONG).show()
                    StateManager.selectedDoctor = User(
                        user.id,
                        etNameEdit.text.toString(),
                        etLastNameEdit.text.toString(),
                        etAgeEdit.text.toString().toInt(),
                        etImageEdit.text.toString(),
                        etEmailEdit.text.toString(),
                        etSpecialistEdit.text.toString(),
                        user.recommendation,
                        etWorkplaceEdit.text.toString(),
                        etBiographyEdit.text.toString(),
                    )
                    finish()
                }
                else
                    Toast.makeText(this@EditUserProfileActivity, "Error al editar el perfil", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<RegisterUpdateResponse>, t: Throwable) {
                Toast.makeText(this@EditUserProfileActivity, "Error al conectar al server: ${t.message}", Toast.LENGTH_LONG).show()
            }

        })

        /*val intent = Intent(this, UserProfileActivity::class.java)
        startActivity(intent)*/
    }
}