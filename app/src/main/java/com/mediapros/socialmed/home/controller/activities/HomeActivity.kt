package com.mediapros.socialmed.home.controller.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mediapros.socialmed.R
import com.mediapros.socialmed.RetrofitBuilder
import com.mediapros.socialmed.StateManager
import com.mediapros.socialmed.errors.controller.activities.ReportErrorsActivity
import com.mediapros.socialmed.home.adapter.RecommendedDoctorAdapter
import com.mediapros.socialmed.security.controller.activities.RegisterActivity
import com.mediapros.socialmed.security.models.User
import com.mediapros.socialmed.security.network.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {
    var recommendedDoctors: List<User> = ArrayList()
    lateinit var rvRecommendedDoctors: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        rvRecommendedDoctors = findViewById(R.id.rvRecommendedDoctors)

        loadRecommendedDoctors()
        val bnvMenu = findViewById<BottomNavigationView>(R.id.bnvMenu)
        bnvMenu.selectedItemId = R.id.menu_errors
        

        val btReportError = findViewById<Button>(R.id.btReportError)



        btReportError.setOnClickListener {
            goToReportErrorActivity()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_errors) goToReportErrorActivity()
        return super.onOptionsItemSelected(item)
    }

    private fun goToReportErrorActivity() {
        val intentError = Intent(this, ReportErrorsActivity::class.java)

        startActivity(intentError)
    }

    private fun loadRecommendedDoctors() {

        val token = StateManager.authToken

        val retrofit = RetrofitBuilder.build()
        val userService = retrofit.create(UserService::class.java)

        val request = userService.getAllUsers(token)

        request.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    recommendedDoctors = response.body()!!
                    rvRecommendedDoctors.layoutManager = LinearLayoutManager(this@HomeActivity)
                    rvRecommendedDoctors.adapter = RecommendedDoctorAdapter(recommendedDoctors, this@HomeActivity)
                }
                else {
                    println("No se pudo obtener a los usuarios.")
                }

            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                println("No se pudo obtener a los usuarios.")
                println(t.message)
            }

        })


    }
}