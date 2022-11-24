package com.mediapros.socialmed.security.controller.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.mediapros.socialmed.R
import com.mediapros.socialmed.shared.RetrofitBuilder
import com.mediapros.socialmed.security.models.RegisterRequest
import com.mediapros.socialmed.security.models.RegisterUpdateResponse
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
        val checkBoxTerms = findViewById<CheckBox>(R.id.checkBoxTerms)

        if (checkBoxTerms.isChecked) {
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

            request.enqueue(object : Callback<RegisterUpdateResponse> {
                override fun onResponse(call: Call<RegisterUpdateResponse>, response: Response<RegisterUpdateResponse>) {
                    if(response.isSuccessful) {
                        Toast.makeText(this@RegisterActivity, response.body()!!.message, Toast.LENGTH_LONG).show()
                        finish()
                    }
                    else
                        Toast.makeText(this@RegisterActivity, "Error al registrar usuario: ${response.message()}", Toast.LENGTH_LONG).show()
                }

                override fun onFailure(call: Call<RegisterUpdateResponse>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity, "Error al registrar usuario: ${t.message}", Toast.LENGTH_LONG).show()
                }

            })
        }
        else
            Toast.makeText(this, "Debe aceprtar los términos y condiciones para registrarse.", Toast.LENGTH_LONG).show()
    }
}