package com.mediapros.socialmed.forums.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mediapros.socialmed.R
import com.mediapros.socialmed.shared.RetrofitBuilder
import com.mediapros.socialmed.forums.models.Forum
import com.mediapros.socialmed.security.models.User
import com.mediapros.socialmed.security.network.UserService
import com.mediapros.socialmed.shared.OnItemClickListener
import com.mediapros.socialmed.shared.StateManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForumAdapter(private val forums: List<Forum>, private val context: Context, private val itemClickListener: OnItemClickListener<Forum>)
    :RecyclerView.Adapter<ForumAdapter.Prototype>(){
    class Prototype(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvForumTitle = itemView.findViewById<TextView>(R.id.tvForumTitle)
        private val tvForumAuthor = itemView.findViewById<TextView>(R.id.tvForumAuthor)
        private val cvForum = itemView.findViewById<CardView>(R.id.cvForum)

        private fun getAuthorName(userId: Int, context: Context, textView: TextView) {
            val retrofit = RetrofitBuilder.build()
            val userService = retrofit.create(UserService::class.java)
            val request = userService.getUserById(StateManager.authToken, userId)

            request.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful)
                    {
                        textView.text = "${response.body()!!.name} ${response.body()!!.lastName}"
                    }
                    else
                        Toast.makeText(context, "Error al obtener nombre del autor.", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(context, "Error al obtener nombre del autor.", Toast.LENGTH_SHORT).show()
                }

            })
        }

        fun bind(forum: Forum, itemClickListener: OnItemClickListener<Forum>, context: Context) {
            tvForumTitle.text = forum.title
            getAuthorName(forum.userId, context, tvForumAuthor)
            cvForum.setOnClickListener {
                itemClickListener.onItemClicked(forum)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Prototype {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.prototype_forum, parent, false)
        return Prototype(view)
    }

    override fun onBindViewHolder(holder: Prototype, position: Int) {
        holder.bind(forums[position], itemClickListener, context)
    }

    override fun getItemCount(): Int {
        return forums.size
    }
}