package com.example.feedpalapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class RegisterActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var loginUserTextView: TextView
    private lateinit var registerButton: Button
    private lateinit var registrationUrl: String // Declare it here, initialize in onCreate

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_layout)

        // Initialize registrationUrl here, after the context is available
        registrationUrl = getString(R.string.registration_url, getString(R.string.server_ip))

        emailEditText = findViewById(R.id.EditTextEmail)
        usernameEditText = findViewById(R.id.EditTextUsername)
        passwordEditText = findViewById(R.id.EditTextPassword)
        confirmPasswordEditText = findViewById(R.id.EditTextConfirmPassword)
        loginUserTextView = findViewById(R.id.TextViewLoginLink)
        registerButton = findViewById(R.id.ButtonRegister)

        loginUserTextView.setOnClickListener {
            toLoginActivity()
        }

        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            Log.d("RegisterActivity", "Email: $email, Username: $username, Password: $password")

            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (username.isEmpty()) {
                Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            registerUser(email, username, password)
        }
    }

    private fun registerUser(email: String, username: String, password: String) {
        val requestBody = FormBody.Builder()
            .add("email", email)
            .add("username", username)
            .add("password", password)
            .build()

        val request = Request.Builder()
            .url(registrationUrl)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@RegisterActivity, "Registration failed: Network error", Toast.LENGTH_LONG).show()
                    Log.e("RegisterActivity", "Network error during registration", e)
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                runOnUiThread {
                    if (response.isSuccessful && responseBody != null) {
                        Log.d("Registration Response", "Raw Response: $responseBody") // Log the raw response
                        try {
                            val jsonResponse = org.json.JSONObject(responseBody)
                            val status = jsonResponse.getString("status")
                            val message = jsonResponse.getString("message")

                            if (status == "success") {
                                Toast.makeText(this@RegisterActivity, "Registration successful! Please log in.", Toast.LENGTH_LONG).show()
                                toLoginActivity()
                                finish() // Close the registration activity
                            } else {
                                Toast.makeText(this@RegisterActivity, "Registration failed: $message", Toast.LENGTH_LONG).show()
                            }
                        } catch (e: org.json.JSONException) {
                            Toast.makeText(this@RegisterActivity, "Registration failed: Error parsing response", Toast.LENGTH_LONG).show()
                            Log.e("RegisterActivity", "Error parsing JSON response", e)
                        }
                    } else {
                        Toast.makeText(this@RegisterActivity, "Registration failed: Server error", Toast.LENGTH_LONG).show()
                        Log.e("RegisterActivity", "Server error during registration: $responseBody")
                    }
                }
            }
        })
    }

    fun toLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}