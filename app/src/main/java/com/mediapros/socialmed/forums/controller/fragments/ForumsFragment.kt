package com.mediapros.socialmed.forums.controller.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mediapros.socialmed.R
import com.mediapros.socialmed.shared.RetrofitBuilder
import com.mediapros.socialmed.forums.adapter.ForumAdapter
import com.mediapros.socialmed.forums.controller.activities.CreateForumActivity
import com.mediapros.socialmed.forums.controller.activities.ForumDetailsActivity
import com.mediapros.socialmed.forums.models.Forum
import com.mediapros.socialmed.forums.network.ForumService
import com.mediapros.socialmed.shared.OnItemClickListener
import com.mediapros.socialmed.shared.StateManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ForumsFragment : Fragment(), OnItemClickListener<Forum> {
    lateinit var recyclerView: RecyclerView
    var forums: List<Forum> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forums, container, false)
    }

    override fun onResume() {
        super.onResume()
        loadForums()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btAddForum = view.findViewById<ImageButton>(R.id.btAddForum)
        recyclerView = view.findViewById(R.id.rvForums)
        loadForums()
        btAddForum.setOnClickListener {
            goToCreateForumActivity()
        }
    }

    private fun goToCreateForumActivity() {
        val intent = Intent(context, CreateForumActivity::class.java)
        startActivity(intent)
    }

    private fun loadForums() {
        val token = StateManager.authToken

        val retrofit = RetrofitBuilder.build()
        val forumService = retrofit.create(ForumService::class.java)

        val request = forumService.getAllForums(token)

        request.enqueue(object : Callback<List<Forum>> {
            override fun onResponse(call: Call<List<Forum>>, response: Response<List<Forum>>) {
                if (response.isSuccessful) {
                    forums = response.body()!!
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    recyclerView.adapter = ForumAdapter(forums, context!!, this@ForumsFragment)
                }
                else
                    Toast.makeText(context, "Error al obtener foros.", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<List<Forum>>, t: Throwable) {
                Toast.makeText(context, "Error al obtener foros: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onItemClicked(value: Forum) {
        StateManager.selectedForum = value
        val intent = Intent(context, ForumDetailsActivity::class.java)
        startActivity(intent)
    }
}