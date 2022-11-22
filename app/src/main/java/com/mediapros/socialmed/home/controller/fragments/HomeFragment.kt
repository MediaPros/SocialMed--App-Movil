package com.mediapros.socialmed.home.controller.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mediapros.socialmed.R
import com.mediapros.socialmed.shared.RetrofitBuilder
import com.mediapros.socialmed.shared.StateManager
import com.mediapros.socialmed.home.adapter.RecommendedDoctorAdapter
import com.mediapros.socialmed.home.models.Joke
import com.mediapros.socialmed.home.network.JokeService
import com.mediapros.socialmed.interconsultation.controller.activities.UserProfileActivity
import com.mediapros.socialmed.security.models.User
import com.mediapros.socialmed.security.network.UserService
import com.mediapros.socialmed.shared.OnItemClickListener
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment(), OnItemClickListener<User> {

    var recommendedDoctors: List<User> = ArrayList()
    lateinit var rvRecommendedDoctors: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvRecommendedDoctors = view.findViewById(R.id.rvRecommendedDoctors)
        loadRecommendedDoctors()
        loadJoke(view)
    }

    private fun loadJoke(view: View) {
        val tvJoke = view.findViewById<TextView>(R.id.tvJoke)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://icanhazdadjoke.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val jokeService = retrofit.create(JokeService::class.java)
        val request = jokeService.getJoke()

        request.enqueue(object : Callback<Joke> {
            override fun onResponse(call: Call<Joke>, response: Response<Joke>) {
                if (response.isSuccessful)
                    tvJoke.text = response.body()!!.text
                else
                    Toast.makeText(context, "Error al obtener chiste.", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<Joke>, t: Throwable) {
                Toast.makeText(context, "Error al obtener chiste: ${t.message}", Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun onResume() {
        super.onResume()
        loadRecommendedDoctors()
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
                    rvRecommendedDoctors.layoutManager = LinearLayoutManager(context)
                    rvRecommendedDoctors.adapter = RecommendedDoctorAdapter(recommendedDoctors, context!!, this@HomeFragment)
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

    override fun onItemClicked(value: User) {
        StateManager.selectedDoctor = value
        val intent = Intent(context, UserProfileActivity::class.java)
        startActivity(intent)
    }
}