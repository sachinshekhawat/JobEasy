package com.example.jobeasy

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)
        val btv = findViewById<BottomNavigationView>(R.id.bottom_nav);

        NavigationUI.setupWithNavController(btv, navController)

        btv.setOnItemReselectedListener {
            // do nothing to prevent reload
        }

        btv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bookmarksFragment -> {
                    // Only navigate if it's not already the current destination
                    if (navController.currentDestination?.id != R.id.bookmarksFragment) {
                        navController.navigate(R.id.bookmarksFragment)
                    }
                    true
                }
                R.id.jobsFragment -> {
                    // Only navigate if it's not already the current destination
                    if (navController.currentDestination?.id != R.id.jobsFragment) {
                        navController.navigate(R.id.jobsFragment)
                    }
                    true
                }
                else -> false
            }
        }

    }

    // Enable the "Up" button to navigate back
    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp() || super.onSupportNavigateUp()
    }
}