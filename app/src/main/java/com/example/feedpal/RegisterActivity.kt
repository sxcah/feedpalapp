package com.example.feedpal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val loginLinkTextView: TextView = findViewById(R.id.textViewLoginLink)
        loginLinkTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val registerButton: Button = findViewById(R.id.buttonRegister)
        registerButton.setOnClickListener {
            // TODO: Implement your registration logic here
            // For now, let's just print a message
            println("Register button clicked")
        }
    }
}