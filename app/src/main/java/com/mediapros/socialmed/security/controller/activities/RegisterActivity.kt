package com.mediapros.socialmed.security.controller.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.mediapros.socialmed.R
import com.mediapros.socialmed.RetrofitBuilder
import com.mediapros.socialmed.security.models.RegisterRequest
import com.mediapros.socialmed.security.network.UserService
import retrofit2.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btRegister2 = findViewById<Button>(R.id.btRegister2)
        btRegister2.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val etEmail2 = findViewById<EditText>(R.id.etEmail2)
        val etPassword2 = findViewById<EditText>(R.id.etPassword2)
        val etName = findViewById<EditText>(R.id.etName)
        val etLastName = findViewById<EditText>(R.id.etLastName)
        val etAge = findViewById<EditText>(R.id.etAge)
        val etImage = findViewById<EditText>(R.id.etImage)
        val etSpecialist = findViewById<EditText>(R.id.etSpecialist)
        val etWorkplace = findViewById<EditText>(R.id.etWorkplace)
        val etBiography = findViewById<EditText>(R.id.etBiography)

        val retrofit = RetrofitBuilder.build()

        val userService: UserService = retrofit.create(UserService::class.java)

        val request = userService.signUp(RegisterRequest(
            etName.text.toString(),
            etLastName.text.toString(),
            etAge.text.toString().toInt(),
            etImage.text.toString(),
            etSpecialist.text.toString(),
            etWorkplace.text.toString(),
            etBiography.text.toString(),
            etEmail2.text.toString(),
            etPassword2.text.toString()
        ))

        request.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful) {
                    val tvRegister2 = findViewById<TextView>(R.id.tvRegister2)
                    tvRegister2.text = response.body()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                TODO("Not implemented")
            }

        })
    }
}