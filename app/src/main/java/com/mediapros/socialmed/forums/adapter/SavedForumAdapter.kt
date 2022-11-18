package com.mediapros.socialmed.forums.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mediapros.socialmed.R
import com.mediapros.socialmed.forums.models.SavedForum
import com.mediapros.socialmed.shared.OnItemClickListener

class SavedForumAdapter(private val forums: List<SavedForum>, private val context: Context, private val itemClickListener: OnItemClickListener<SavedForum>)
    :RecyclerView.Adapter<SavedForumAdapter.Prototype>(){
    class Prototype(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvForumTitle = itemView.findViewById<TextView>(R.id.tvForumTitle)
        private val tvForumAuthor = itemView.findViewById<TextView>(R.id.tvForumAuthor)
        private val cvForum = itemView.findViewById<CardView>(R.id.cvForum)

        fun bind(forum: SavedForum, itemClickListener: OnItemClickListener<SavedForum>) {
            tvForumTitle.text = forum.title
            tvForumAuthor.text = forum.author
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
        holder.bind(forums[position], itemClickListener)
    }

    override fun getItemCount(): Int {
        return forums.size
    }
}