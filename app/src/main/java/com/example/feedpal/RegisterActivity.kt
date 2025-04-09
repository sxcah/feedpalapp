package com.example.feedpal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

val registeredUsers = mutableListOf<User>()

data class User(val email: String, val username: String, val passwordHash: String)


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
            finish()
        }

        buttonRegister.setOnClickListener {
            val email = editTextRegisterEmail.text.toString().trim()
            val username = editTextRegisterUsername.text.toString().trim()
            val password = editTextRegisterPassword.text.toString().trim()
            val confirmPassword = editTextRegisterConfirmPassword.text.toString().trim()

            if (email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    // For demonstration, we'll "hash" the password (very insecure!)
                    val passwordHash = password // In a real app, use a proper hashing algorithm

                    // Check if a user with this email or username already exists
                    val existingUser = registeredUsers.find { it.email == email || it.username == username }

                    if (existingUser == null) {
                        val newUser = User(email, username, passwordHash)
                        registeredUsers.add(newUser)
                        Log.d("Registration", "New user registered: $newUser")
                        Toast.makeText(this@RegisterActivity, "Registration successful!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        if (existingUser.email == email) {
                            editTextRegisterEmail.error = "Email already exists"
                        }
                        if (existingUser.username == username) {
                            editTextRegisterUsername.error = "Username already exists"
                        }
                        Toast.makeText(this@RegisterActivity, "User with this email or username already exists.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    editTextRegisterConfirmPassword.error = "Passwords do not match"
                    Toast.makeText(this@RegisterActivity, "Passwords do not match.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}