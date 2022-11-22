package com.mediapros.socialmed.home.controller.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.mediapros.socialmed.R
import com.mediapros.socialmed.forums.controller.activities.SavedForumsActivity
import com.mediapros.socialmed.home.controller.activities.SavedJokesActivity


class MoreFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cvSForumOption = view.findViewById<CardView>(R.id.cvSForumOption)
        val cvSJokeOption = view.findViewById<CardView>(R.id.cvSJokeOption)
        cvSForumOption.setOnClickListener {
            goToSavedForumsActivity(view.context)
        }
        cvSJokeOption.setOnClickListener {
            goToSavedJokesActivity(view.context)
        }
    }

    private fun goToSavedForumsActivity(context: Context) {
        val intent = Intent(context, SavedForumsActivity::class.java)
        startActivity(intent)
    }
    private fun goToSavedJokesActivity(context: Context) {
        val intent = Intent(context, SavedJokesActivity::class.java)
        startActivity(intent)
    }
}