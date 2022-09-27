package com.mediapros.socialmed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.mediapros.socialmed.security.models.AuthenticateRequest
import com.mediapros.socialmed.security.models.AuthenticateResponse
import com.mediapros.socialmed.security.network.UserService
import retrofit2.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btLogin = findViewById<Button>(R.id.btLogin)
        val btRegister = findViewById<Button>(R.id.btRegister)

        btLogin.setOnClickListener {
            signIn()
        }

        btRegister.setOnClickListener {
            register()
        }
    }

    private fun register() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun signIn() {
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val retrofit = RetrofitBuilder.build()

        val userService: UserService = retrofit.create(UserService::class.java)


        val request = userService.signIn(AuthenticateRequest(etEmail.text.toString(), etPassword.text.toString()))

        request.enqueue(object: Callback<AuthenticateResponse> {
            override fun onResponse(
                call: Call<AuthenticateResponse>,
                response: Response<AuthenticateResponse>
            ) {
                if (response.isSuccessful)
                    Toast.makeText(this@MainActivity, response.body()!!.token, Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this@MainActivity, "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<AuthenticateResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Usuario o contraseña incorrectos.", Toast.LENGTH_SHORT).show()
            }

        })
    }
}