package com.mediapros.socialmed.forums.controller.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mediapros.socialmed.R
import com.mediapros.socialmed.forums.adapter.CommentAdapter
import com.mediapros.socialmed.forums.controller.dialogs.CreateCommentDialog
import com.mediapros.socialmed.forums.models.Comment
import com.mediapros.socialmed.forums.models.SaveCommentResource
import com.mediapros.socialmed.forums.network.CommentService
import com.mediapros.socialmed.security.models.User
import com.mediapros.socialmed.security.network.UserService
import com.mediapros.socialmed.shared.RetrofitBuilder
import com.mediapros.socialmed.shared.StateManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import java.util.*
import kotlin.collections.ArrayList

class ForumDetailsActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    var comments: List<Comment> = ArrayList()
    val forum = StateManager.selectedForum

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forum_details)
        recyclerView = findViewById(R.id.rvComments)
        val btCreateComment = findViewById<ImageButton>(R.id.btCreateComment)
        loadForumDetails()
        loadComments()

        btCreateComment.setOnClickListener {
            showCreateCommentDialog()
        }

    }

    override fun onResume() {
        super.onResume()
        loadForumDetails()
        loadComments()
    }

    private fun showCreateCommentDialog() {
        CreateCommentDialog(
            onSendClickListener = { comment ->
                createComment(comment)
            }
        ).show(supportFragmentManager, "commentDialog")
    }

    private fun createComment(comment: String) {
        val retrofit = RetrofitBuilder.build()
        val commentService = retrofit.create(CommentService::class.java)

        val request = commentService.createComment(StateManager.authToken,
            SaveCommentResource(
                StateManager.getJSDate(Date()),
                comment,
                StateManager.userId,
                forum.id
            )
        )

        request.enqueue(object : Callback<Comment> {
            override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
                if (response.isSuccessful)
                {
                    Toast.makeText(this@ForumDetailsActivity, "Comentario creado.", Toast.LENGTH_LONG).show()
                    loadComments()
                }
                else
                    Toast.makeText(this@ForumDetailsActivity, "Error al crear comentario.", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<Comment>, t: Throwable) {
                Toast.makeText(this@ForumDetailsActivity, "Error al crear comentario: ${t.message}", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun loadComments() {
        val token = StateManager.authToken

        val retrofit = RetrofitBuilder.build()
        val commentService = retrofit.create(CommentService::class.java)

        val request = commentService.getCommentsByForumId(token, forum.id)

        request.enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if (response.isSuccessful) {
                    comments = response.body()!!
                    recyclerView.layoutManager = LinearLayoutManager(this@ForumDetailsActivity)
                    recyclerView.adapter = CommentAdapter(comments, this@ForumDetailsActivity)
                }
                else
                    Toast.makeText(this@ForumDetailsActivity, "Error al obtener comentarios.", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                Toast.makeText(this@ForumDetailsActivity, "Error al obtener comentarios: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun loadForumDetails() {

        val tvForumDetTitle = findViewById<TextView>(R.id.tvForumDetTitle)
        val tvForumDetAuthor = findViewById<TextView>(R.id.tvForumDetAuthor)
        val tvForumDetContent = findViewById<TextView>(R.id.tvForumDelContent)

        tvForumDetTitle.text = forum.title
        tvForumDetContent.text = forum.content

        val retrofit = RetrofitBuilder.build()
        val userService = retrofit.create(UserService::class.java)
        val request = userService.getUserById(StateManager.authToken, forum.userId)

        request.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful)
                {
                    tvForumDetAuthor.text = "${response.body()!!.name} ${response.body()!!.lastName}"
                }
                else
                    Toast.makeText(this@ForumDetailsActivity, "Error al obtener nombre del autor.", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@ForumDetailsActivity, "Error al obtener nombre del autor.", Toast.LENGTH_SHORT).show()
            }

        })
    }
}