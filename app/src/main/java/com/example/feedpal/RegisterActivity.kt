package com.example.feedpal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity() {

    private lateinit var editTextRegisterEmail: EditText
    private lateinit var editTextRegisterUsername: EditText
    private lateinit var editTextRegisterPassword: EditText
    private lateinit var editTextRegisterConfirmPassword: EditText
    private lateinit var buttonRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        editTextRegisterEmail = findViewById(R.id.editTextRegisterEmail)
        editTextRegisterUsername = findViewById(R.id.editTextRegisterUsername)
        editTextRegisterPassword = findViewById(R.id.editTextRegisterPassword)
        editTextRegisterConfirmPassword = findViewById(R.id.editTextRegisterConfirmPassword)
        buttonRegister = findViewById(R.id.buttonRegister)
        val loginLinkTextView: TextView = findViewById(R.id.textViewLoginLink)

        loginLinkTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Optional: Close the RegisterActivity
        }

        buttonRegister.setOnClickListener {
            val email = editTextRegisterEmail.text.toString().trim()
            val username = editTextRegisterUsername.text.toString().trim()
            val password = editTextRegisterPassword.text.toString().trim()
            val confirmPassword = editTextRegisterConfirmPassword.text.toString().trim()

            if (email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                // For this simple redirection, we just check if all fields are filled
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish() // Close the RegisterActivity after redirection
            } else {
                Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}