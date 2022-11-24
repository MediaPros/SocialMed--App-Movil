package com.mediapros.socialmed.forums.controller.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mediapros.socialmed.R
import com.mediapros.socialmed.forums.adapter.CommentAdapter
import com.mediapros.socialmed.forums.controller.dialogs.CreateCommentDialog
import com.mediapros.socialmed.forums.models.Comment
import com.mediapros.socialmed.forums.models.Forum
import com.mediapros.socialmed.forums.models.SaveCommentResource
import com.mediapros.socialmed.forums.models.SavedForum
import com.mediapros.socialmed.forums.network.CommentService
import com.mediapros.socialmed.forums.network.ForumService
import com.mediapros.socialmed.security.models.User
import com.mediapros.socialmed.security.network.UserService
import com.mediapros.socialmed.shared.AppDatabase
import com.mediapros.socialmed.shared.OnItemClickListener
import com.mediapros.socialmed.shared.RetrofitBuilder
import com.mediapros.socialmed.shared.StateManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class ForumDetailsActivity : AppCompatActivity(), OnItemClickListener<Comment> {
    lateinit var recyclerView: RecyclerView
    var comments: List<Comment> = ArrayList()
    val forum = StateManager.selectedForum
    lateinit var author: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forum_details)
        recyclerView = findViewById(R.id.rvComments)
        val btDeleteForum = findViewById<ImageButton>(R.id.btDeleteForum)
        val btCreateComment = findViewById<ImageButton>(R.id.btCreateComment)
        val btSaveForum = findViewById<ImageButton>(R.id.btSaveForum)
        loadForumDetails()
        loadComments()

        btCreateComment.setOnClickListener {
            showCreateCommentDialog()
        }

        btSaveForum.setOnClickListener {
            saveForumOnDb()
        }

        if (forum.userId == StateManager.loggedUserId) {
            btDeleteForum.visibility = View.VISIBLE
            btDeleteForum.setOnClickListener {
                deleteForum()
            }
        }

    }

    private fun saveForumOnDb() {
        val query = AppDatabase.getInstance(this).getSavedForumDao().getByForumIdAndTitle(forum.id, forum.title)
        if (query.isEmpty()) {
            AppDatabase.getInstance(this).getSavedForumDao().insertForum(
                SavedForum(null, forum.id, forum.title, author, forum.content, forum.date)
            )
            Toast.makeText(this, "Entrada guardada en dispositivo.", Toast.LENGTH_LONG).show()
        }
        else
            Toast.makeText(this, "La entrada ya está guardada en el dispositivo.", Toast.LENGTH_LONG).show()

    }

    private fun deleteForum() {
        val retrofit = RetrofitBuilder.build()
        val forumService = retrofit.create(ForumService::class.java)

        val request = forumService.deleteForum(StateManager.authToken, forum.id)

        request.enqueue(object : Callback<Forum> {
            override fun onResponse(call: Call<Forum>, response: Response<Forum>) {
                if (response.isSuccessful)
                {
                    Toast.makeText(this@ForumDetailsActivity, "Entrada del foro eliminada.", Toast.LENGTH_LONG).show()
                    finish()
                }
                else
                    Toast.makeText(this@ForumDetailsActivity, "Error al eliminar la entrada del foro.", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<Forum>, t: Throwable) {
                Toast.makeText(this@ForumDetailsActivity, "Error al eliminar la entrada del foro: ${t.message}", Toast.LENGTH_LONG).show()
            }

        })
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
                StateManager.loggedUserId,
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
                    recyclerView.adapter = CommentAdapter(comments, this@ForumDetailsActivity, this@ForumDetailsActivity)
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
        val tvForumDetContent = findViewById<TextView>(R.id.tvForumDetContent)

        tvForumDetTitle.text = forum.title
        tvForumDetContent.text = forum.content

        val retrofit = RetrofitBuilder.build()
        val userService = retrofit.create(UserService::class.java)
        val request = userService.getUserById(StateManager.authToken, forum.userId)

        request.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful)
                {
                    author = "${response.body()!!.name} ${response.body()!!.lastName}"
                    tvForumDetAuthor.text = author
                }
                else
                    Toast.makeText(this@ForumDetailsActivity, "Error al obtener nombre del autor.", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@ForumDetailsActivity, "Error al obtener nombre del autor.", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun deleteComment(comment: Comment) {
        val retrofit = RetrofitBuilder.build()
        val commentService = retrofit.create(CommentService::class.java)

        val request = commentService.deleteComment(StateManager.authToken, comment.id)

        request.enqueue(object : Callback<Comment> {
            override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@ForumDetailsActivity, "Comentario eliminado.", Toast.LENGTH_LONG).show()
                    loadComments()
                }
                else
                    Toast.makeText(this@ForumDetailsActivity, "Error al eliminar el comentario.", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<Comment>, t: Throwable) {
                Toast.makeText(this@ForumDetailsActivity, "Error al eliminar el comentario: ${t.message}", Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun onItemClicked(value: Comment) {
        deleteComment(value)
    }
}