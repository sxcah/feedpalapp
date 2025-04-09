package com.example.feedpal;

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileImgPlaceholder: ImageView
    private lateinit var profileTextUsername: TextView
    private lateinit var profileTextEmail: TextView
    private lateinit var buttonLogout: Button
    private lateinit var backButtonProfile: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        profileImgPlaceholder = findViewById(R.id.profileImgPlaceholder)
        profileTextUsername = findViewById(R.id.profileTextUsername)
        profileTextEmail = findViewById(R.id.profileTextEmail)
        buttonLogout = findViewById(R.id.buttonLogout)
        backButtonProfile = findViewById(R.id.backButtonProfile)

        val loggedInUsername = intent.getStringExtra("loggedInUsername")
        val loggedInEmail = intent.getStringExtra("loggedInEmail")

        if (!loggedInUsername.isNullOrEmpty()) {
            profileTextUsername.text = loggedInUsername
        } else {
            profileTextUsername.text = "Guest" // Set a default value
        }
        if (!loggedInEmail.isNullOrEmpty()) {
            profileTextEmail.text = loggedInEmail
        } else {
            profileTextEmail.text = "guest@example.com" // Set a default value
        }

        buttonLogout.setOnClickListener {
            clearLoggedInUser()
            val intent = Intent(this, LoginActivity::class.java)
            // Flags to clear the activity stack and prevent going back to HomeActivity
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish() // Finish the ProfileActivity
        }

        backButtonProfile.setOnClickListener() {
            onBackPressed()
        }
    }

    private fun clearLoggedInUser() {
        // Implement your logic to clear the logged-in user's session.
        // This might involve clearing SharedPreferences, removing tokens, etc.
        val sharedPref = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            clear()
            apply()
        }
        // Optionally, you might also want to clear the in-memory registeredUsers list
        // if you are not persisting user data.
        // AppGlobals.registeredUsers.clear()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}