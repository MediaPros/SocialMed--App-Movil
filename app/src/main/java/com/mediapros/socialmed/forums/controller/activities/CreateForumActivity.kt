package com.mediapros.socialmed.forums.controller.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.mediapros.socialmed.R
import com.mediapros.socialmed.forums.models.Forum
import com.mediapros.socialmed.forums.models.SaveForumResource
import com.mediapros.socialmed.forums.network.ForumService
import com.mediapros.socialmed.shared.RetrofitBuilder
import com.mediapros.socialmed.shared.StateManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class CreateForumActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_forum)
        val btCreateForum = findViewById<Button>(R.id.btCreateForum)
        btCreateForum.setOnClickListener {
            createForum()
        }
    }

    private fun createForum() {
        val etForumTitle = findViewById<EditText>(R.id.etForumTitle)
        val etForumContent = findViewById<EditText>(R.id.etForumContent)

        val title = etForumTitle.text.toString()
        val content = etForumContent.text.toString()
        val date = StateManager.getJSDate(Date())

        val retrofit = RetrofitBuilder.build()
        val forumService = retrofit.create(ForumService::class.java)
        val request = forumService.saveForum(StateManager.authToken,
            SaveForumResource(title,
                content,
                date,
                StateManager.loggedUserId))

        request.enqueue(object : Callback<Forum> {
            override fun onResponse(call: Call<Forum>, response: Response<Forum>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@CreateForumActivity, "Se ha creado la entrada en el foro.", Toast.LENGTH_LONG).show()
                    finish()
                }
                else
                    Toast.makeText(this@CreateForumActivity, "Error al crear la entrada en el foro.", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<Forum>, t: Throwable) {
                Toast.makeText(this@CreateForumActivity, "Error al crear la entrada en el foro: ${t.message}", Toast.LENGTH_LONG).show()
            }

        })
    }
}