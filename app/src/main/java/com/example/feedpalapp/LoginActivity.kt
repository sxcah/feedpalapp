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

class LoginActivity : AppCompatActivity() {

    private lateinit var emailUsernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerUserTextView: TextView
    private lateinit var loginUrl: String
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

        loginUrl = getString(R.string.login_url, getString(R.string.server_ip))

        emailUsernameEditText = findViewById(R.id.EditTextLoginEmail)
        passwordEditText = findViewById(R.id.EditTextPassword)
        loginButton = findViewById(R.id.ButtonLogin)
        registerUserTextView = findViewById(R.id.TextViewRegisterLink)

        registerUserTextView.setOnClickListener {
            toRegisterActivity()
        }

        loginButton.setOnClickListener {
            val emailUsername = emailUsernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            Log.d("LoginActivity", "Email: $emailUsername, Password: $password")

            if (emailUsername.isEmpty()) {
                Toast.makeText(this, "Please enter your email or username", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            loginUser(emailUsername, password)
        }
    }

    private fun loginUser(emailUsername: String, password: String) {
        val requestBody = FormBody.Builder()
            .add("email_username", emailUsername)
            .add("password", password)
            .build()

        val request = Request.Builder()
            .url(loginUrl)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@LoginActivity, "Login failed: Network error", Toast.LENGTH_LONG).show()
                    Log.e("LoginActivity", "Network error during login", e)
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                runOnUiThread {
                    if (response.isSuccessful && responseBody != null) {
                        Log.d("Login Response", "Raw Response: $responseBody") // Log raw response
                        try {
                            val jsonResponse = org.json.JSONObject(responseBody)
                            val status = jsonResponse.getString("status")
                            val message = jsonResponse.getString("message")

                            if (status == "success") {
                                Toast.makeText(this@LoginActivity, "Login successful!", Toast.LENGTH_LONG).show()
                                toHomePageActivity()
                            } else {
                                Toast.makeText(this@LoginActivity, "Login failed: $message", Toast.LENGTH_LONG).show()
                            }
                        } catch (e: org.json.JSONException) {
                            Toast.makeText(this@LoginActivity, "Login failed: Error parsing response", Toast.LENGTH_LONG).show()
                            Log.e("LoginActivity", "Error parsing JSON response", e)
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, "Login failed: Server error", Toast.LENGTH_LONG).show()
                        Log.e("LoginActivity", "Server error during login: $responseBody")
                    }
                }
            }
        })
    }

    fun toHomePageActivity() {
        val intent = Intent(this, HomePageActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun toRegisterActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}