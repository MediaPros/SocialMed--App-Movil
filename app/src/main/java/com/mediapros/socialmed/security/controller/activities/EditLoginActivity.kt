package com.mediapros.socialmed.security.controller.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.mediapros.socialmed.R
import com.mediapros.socialmed.interconsultation.controller.activities.UserProfileActivity
import com.mediapros.socialmed.security.models.AuthenticateRequest
import com.mediapros.socialmed.security.models.AuthenticateResponse
import com.mediapros.socialmed.security.network.UserService
import com.mediapros.socialmed.shared.RetrofitBuilder
import com.mediapros.socialmed.shared.StateManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditLoginActivity : AppCompatActivity() {
    val user = StateManager.selectedDoctor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_login)
        val btLoginEdit = findViewById<Button>(R.id.btLoginEdit)
        btLoginEdit.setOnClickListener {
            confirmPassword()
        }
    }

    private fun confirmPassword(){
        val etEmail = user.email
        val etPassword = findViewById<EditText>(R.id.etPasswordLoginEdit)
        val retrofit = RetrofitBuilder.build()

        val userService: UserService = retrofit.create(UserService::class.java)


        val request = userService.signIn(AuthenticateRequest(etEmail, etPassword.text.toString()))
        val intent = Intent(this, EditUserProfileActivity::class.java)
        request.enqueue(object: Callback<AuthenticateResponse> {
            override fun onResponse(
                call: Call<AuthenticateResponse>,
                response: Response<AuthenticateResponse>
            ) {
                if (response.isSuccessful) {
                    StateManager.password = etPassword.text.toString()
                    intent.putExtra("password",etPassword.text.toString())
                    startActivity(intent)
                    Toast.makeText(this@EditLoginActivity, "Contraseña correcta", Toast.LENGTH_LONG).show()
                    finish()

                } else {
                    Toast.makeText(this@EditLoginActivity, "Error al ingresar la contraseña", Toast.LENGTH_SHORT)
                        .show()

                }
            }

            override fun onFailure(call: Call<AuthenticateResponse>, t: Throwable) {
                Toast.makeText(this@EditLoginActivity, "Error al ingresar la contraseña", Toast.LENGTH_SHORT).show()
            }

        })


    }

}