package com.mediapros.socialmed.forums.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mediapros.socialmed.R
import com.mediapros.socialmed.forums.models.Comment
import com.mediapros.socialmed.forums.network.CommentService
import com.mediapros.socialmed.security.models.User
import com.mediapros.socialmed.security.network.UserService
import com.mediapros.socialmed.shared.OnItemClickListener
import com.mediapros.socialmed.shared.RetrofitBuilder
import com.mediapros.socialmed.shared.StateManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentAdapter(private val comments: List<Comment>, private val context: Context, private val itemClickListener:OnItemClickListener<Comment>)
    :RecyclerView.Adapter<CommentAdapter.Prototype>(){
    class Prototype(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvCommentAuthor = itemView.findViewById<TextView>(R.id.tvCommentAuthor)
        private val tvCommentContent = itemView.findViewById<TextView>(R.id.tvCommentContent)
        private val btDeleteComment = itemView.findViewById<ImageButton>(R.id.btDeleteComment)

        private fun getAuthorName(userId: Int, context: Context) {
            val retrofit = RetrofitBuilder.build()
            val userService = retrofit.create(UserService::class.java)
            val request = userService.getUserById(StateManager.authToken, userId)

            request.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful)
                    {
                        tvCommentAuthor.text = "${response.body()!!.name} ${response.body()!!.lastName}"
                    }
                    else
                        Toast.makeText(context, "Error al obtener nombre del autor.", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(context, "Error al obtener nombre del autor.", Toast.LENGTH_SHORT).show()
                }

            })
        }

        fun bind(comment: Comment, context: Context, itemClickListener: OnItemClickListener<Comment>) {
            getAuthorName(comment.userId, context)
            tvCommentContent.text = comment.content
            if (comment.userId == StateManager.loggedUserId) {
                btDeleteComment.visibility = View.VISIBLE
                btDeleteComment.setOnClickListener {
                    itemClickListener.onItemClicked(comment)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Prototype {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.prototype_comment, parent, false)
        return Prototype(view)
    }

    override fun onBindViewHolder(holder: Prototype, position: Int) {
        holder.bind(comments[position], context, itemClickListener)
    }

    override fun getItemCount(): Int {
        return comments.size
    }
}