package com.mediapros.socialmed.home.controller.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mediapros.socialmed.R
import com.mediapros.socialmed.errors.controller.fragments.ReportErrorsFragment
import com.mediapros.socialmed.forums.controller.fragments.ForumsFragment
import com.mediapros.socialmed.home.controller.fragments.HomeFragment

class HomeActivity : AppCompatActivity() {

    private val onNavigationItemSelectedListener = BottomNavigationView.
    OnNavigationItemSelectedListener { item -> navigateTo(item) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val navView: BottomNavigationView = findViewById(R.id.bnvMenu)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        navigateTo(navView.menu.findItem(R.id.menu_home))
    }

    private fun navigateTo(item: MenuItem): Boolean {
        item.isChecked = true
        return supportFragmentManager
            .beginTransaction()
            .replace(R.id.flFragment, getFragmentFor(item))
            .commit() > 0
    }

    private fun getFragmentFor(item: MenuItem): Fragment {
        return when(item.itemId) {
            R.id.menu_home -> HomeFragment()
            R.id.menu_forum -> ForumsFragment()
            R.id.menu_errors -> ReportErrorsFragment()
            else -> HomeFragment()
        }
    }
}