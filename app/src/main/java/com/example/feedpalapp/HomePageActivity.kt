// HomePageActivity.kt

package com.example.feedpalapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class HomePageActivity : AppCompatActivity () {

    private lateinit var ButtonLogOut : Button
    private lateinit var UsernameTextView : TextView
    private lateinit var EmailTextView : TextView
    private lateinit var ProfilePlaceholder : ImageView
    private lateinit var logoutUrl: String
    private lateinit var fetchUserUrl: String // New URL to fetch user data
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage_activity)

        logoutUrl = getString(R.string.logout_url, getString(R.string.server_ip))
        fetchUserUrl = getString(R.string.fetch_user_url, getString(R.string.server_ip)) // Assuming you define this

        ButtonLogOut = findViewById(R.id.ButtonLogout)
        UsernameTextView = findViewById(R.id.UserNameTextView)
        EmailTextView = findViewById(R.id.EmailTextView)
        ProfilePlaceholder = findViewById(R.id.ImagePlaceHolder)

        // Fetch user data based on the current logged-in state
        fetchUserData()

        ButtonLogOut.setOnClickListener {
            logoutUser()
        }
    }

    private fun fetchUserData() {
        // You would typically need to send some identifier of the logged-in user
        // to the server (e.g., a session ID or an authentication token)
        // For simplicity in this example, we might assume the server knows
        // the current user based on a session.

        val request = Request.Builder()
            .url(fetchUserUrl)
            .build() // Or potentially a POST request with a session identifier

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@HomePageActivity, "Failed to fetch user data: Network error", Toast.LENGTH_LONG).show()
                    Log.e("HomePageActivity", "Network error fetching user data", e)
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                runOnUiThread {
                    if (response.isSuccessful && responseBody != null) {
                        Log.d("HomePageActivity", "User Data Response: $responseBody")
                        try {
                            val jsonResponse = org.json.JSONObject(responseBody)
                            val status = jsonResponse.getString("status")
                            if (status == "success") {
                                val userData = jsonResponse.getJSONObject("user") // Assuming your JSON has a "user" object
                                val username = userData.getString("username")
                                val email = userData.getString("email")

                                UsernameTextView.text = username
                                EmailTextView.text = email
                                // You might also update the profile placeholder based on data from the server
                            } else {
                                val message = jsonResponse.getString("message")
                                Toast.makeText(this@HomePageActivity, "Failed to fetch user data: $message", Toast.LENGTH_LONG).show()
                                Log.e("HomePageActivity", "Failed to fetch user data: $message")
                                // Optionally, handle cases where user data can't be fetched (e.g., logout)
                            }
                        } catch (e: org.json.JSONException) {
                            Toast.makeText(this@HomePageActivity, "Error parsing user data", Toast.LENGTH_LONG).show()
                            Log.e("HomePageActivity", "Error parsing user data: $responseBody", e)
                        }
                    } else {
                        Toast.makeText(this@HomePageActivity, "Failed to fetch user data: Server error", Toast.LENGTH_LONG).show()
                        Log.e("HomePageActivity", "Server error fetching user data: $responseBody")
                        // Optionally, handle server errors (e.g., logout)
                    }
                }
            }
        })
    }

    private fun logoutUser() {
        val request = Request.Builder()
            .url(logoutUrl)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@HomePageActivity, "Logout failed: Network error", Toast.LENGTH_LONG).show()
                    Log.e("HomePageActivity", "Network error during logout", e)
                }
            }

            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    if (response.isSuccessful) {
                        Toast.makeText(this@HomePageActivity, "Logged out successfully!", Toast.LENGTH_LONG).show()
                        navigateToLoginActivity()
                    } else {
                        Toast.makeText(this@HomePageActivity, "Logout failed: Server error", Toast.LENGTH_LONG).show()
                        Log.e("HomePageActivity", "Server error during logout: ${response.body?.string()}")
                    }
                }
            }
        })
    }

    private fun navigateToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}