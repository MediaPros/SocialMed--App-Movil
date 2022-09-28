package com.mediapros.socialmed.home.controller.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mediapros.socialmed.EXTRA_TOKEN
import com.mediapros.socialmed.EXTRA_USER_ID
import com.mediapros.socialmed.R
import com.mediapros.socialmed.RetrofitBuilder
import com.mediapros.socialmed.errors.controller.activities.ReportErrorsActivity
import com.mediapros.socialmed.home.adapter.RecommendedDoctorAdapter
import com.mediapros.socialmed.security.models.User
import com.mediapros.socialmed.security.network.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {
    var recommendedDoctors: List<User> = ArrayList<User>()
    lateinit var recommendedDoctorAdapter: RecommendedDoctorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        loadRecommendedDoctors()

        val rvRecommendedDoctors = findViewById<RecyclerView>(R.id.rvRecommendedDoctors)
        recommendedDoctorAdapter = RecommendedDoctorAdapter(recommendedDoctors)
        rvRecommendedDoctors.adapter = recommendedDoctorAdapter
        rvRecommendedDoctors.layoutManager = LinearLayoutManager(this)

        val btReportError = findViewById<Button>(R.id.btReportError)

        btReportError.setOnClickListener {
            goToReportErrorActivity()
        }
    }

    private fun goToReportErrorActivity() {
        val userId = intent.getStringExtra(EXTRA_USER_ID)
        val token = intent.getStringExtra(EXTRA_TOKEN)

        val intentError = Intent(this, ReportErrorsActivity::class.java).apply {
            putExtra(EXTRA_USER_ID, userId)
            putExtra(EXTRA_TOKEN, token)
        }
        startActivity(intentError)
    }

    private fun loadRecommendedDoctors() {
        val token = intent.getStringExtra(EXTRA_TOKEN)

        val retrofit = RetrofitBuilder.build()
        val userService = retrofit.create(UserService::class.java)

        val request = userService.getAllUsers(token!!)

        request.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    recommendedDoctors = response.body()!!
                    println(recommendedDoctors)
                }

            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                println("No se pudo obtener a los usuarios.")
                println(t.message)
            }

        })


    }
}