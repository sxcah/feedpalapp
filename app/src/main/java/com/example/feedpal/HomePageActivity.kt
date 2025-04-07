package com.example.feedpal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomePageActivity : AppCompatActivity() {

    private lateinit var cartButton: LinearLayout
    private lateinit var usernameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Initialize views
        cartButton = findViewById(R.id.cartButton)
        usernameTextView = findViewById(R.id.usernameTextView)

        // Set up listeners
        cartButton.setOnClickListener {
            val intent = Intent(this, CheckOutActivity::class.java)
            startActivity(intent)
        }

        // Example: Setting a dummy username
        setUsername("SpyderLounge")

        // You can add logic here to fetch user data, categories, best sellers, etc.
    }

    private fun setUsername(username: String) {
        usernameTextView.text = username
    }

    // This method will be called when any product card is clicked
    fun onProductClicked(view: View) {
        val productId = view.tag as String // Get the tag of the clicked view
        val intent = Intent(this, ProductDetailsActivity::class.java)

        // Pass the product identifier to the ProductDetailsActivity
        intent.putExtra("productId", productId)

        startActivity(intent)
    }
}