package com.mediapros.socialmed.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mediapros.socialmed.R
import com.mediapros.socialmed.home.models.SavedJoke
import com.mediapros.socialmed.shared.OnItemClickListener

class SavedJokeAdapter(private val jokes: List<SavedJoke>, private val onItemClickListener: OnItemClickListener<SavedJoke>): RecyclerView.Adapter<SavedJokeAdapter.Prototype>() {
    class Prototype(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvSavedJoke = itemView.findViewById<TextView>(R.id.tvSavedJoke)
        private val btDeleteJoke = itemView.findViewById<ImageButton>(R.id.btDeleteJoke)
        fun bind(joke: SavedJoke, onItemClickListener: OnItemClickListener<SavedJoke>) {
            tvSavedJoke.text = joke.text
            btDeleteJoke.setOnClickListener {
                onItemClickListener.onItemClicked(joke)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Prototype {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.prototype_joke, parent, false)

        return Prototype(view)
    }

    override fun onBindViewHolder(holder: Prototype, position: Int) {
        holder.bind(jokes[position], onItemClickListener)
    }

    override fun getItemCount(): Int {
        return jokes.size
    }
}