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
import com.mediapros.socialmed.interconsultation.controller.activities.UserProfileActivity
import com.mediapros.socialmed.security.models.User
import com.mediapros.socialmed.security.network.UserService
import com.mediapros.socialmed.shared.RetrofitBuilder
import com.mediapros.socialmed.shared.StateManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create


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
        val cvMyProfileOption = view.findViewById<CardView>(R.id.cvMyProfileOption)
        cvSForumOption.setOnClickListener {
            goToSavedForumsActivity(view.context)
        }
        cvSJokeOption.setOnClickListener {
            goToSavedJokesActivity(view.context)
        }
        cvMyProfileOption.setOnClickListener {
            goToUserProfileActivity(view.context)
        }
    }

    private fun goToUserProfileActivity(context: Context) {
        StateManager.selectedDoctor = StateManager.loggedUser
        val intent = Intent(context, UserProfileActivity::class.java)
        startActivity(intent)
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