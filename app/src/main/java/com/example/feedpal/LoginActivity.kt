package com.example.feedpal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextLoginEmail: EditText
    private lateinit var editTextLoginPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var textViewRegisterLink: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize your UI elements
        editTextLoginEmail = findViewById(R.id.editTextLoginEmail)
        editTextLoginPassword = findViewById(R.id.editTextLoginPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        textViewRegisterLink = findViewById(R.id.textViewRegisterLink)

        textViewRegisterLink.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        buttonLogin.setOnClickListener {
            val emailOrUsername = editTextLoginEmail.text.toString() // Using the same EditText for email or username
            val password = editTextLoginPassword.text.toString()

            // Check against the in-memory registeredUsers list
            val loggedInUser = registeredUsers.find {
                (it.email == emailOrUsername || it.username == emailOrUsername) && it.passwordHash == password // In a real app, compare hashed passwords
            }

            if (loggedInUser != null) {
                // Login successful, navigate to the home page
                val intent = Intent(this, HomePageActivity::class.java)
                intent.putExtra("loggedInUsername", loggedInUser.username)
                intent.putExtra("loggedInEmail", loggedInUser.email)
                startActivity(intent)
                finish() // Optional: Close the LoginActivity
            } else {
                // Login failed, show an error message
                Toast.makeText(this, "Invalid email/username or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}