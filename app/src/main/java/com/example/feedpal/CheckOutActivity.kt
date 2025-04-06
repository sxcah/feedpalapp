package com.example.feedpal

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class CheckOutActivity : AppCompatActivity() {

    private lateinit var homeButton: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)

        // Initialize the Home button from the bottom navigation
        val bottomNavigationView = findViewById<LinearLayout>(R.id.bottomNavigationView)
        homeButton = bottomNavigationView.findViewById(R.id.homeButton)

        // Set up listener for the Home button to go back to HomePageActivity
        homeButton.setOnClickListener {
            val intent = Intent(this, HomePageActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
    }
}