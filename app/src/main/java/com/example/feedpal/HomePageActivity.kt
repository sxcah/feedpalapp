package com.example.feedpal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomePageActivity : AppCompatActivity() {

    private lateinit var cartButton: LinearLayout
    private lateinit var usernameTextView: TextView
    private lateinit var profileHomeButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        cartButton = findViewById(R.id.cartButton)
        usernameTextView = findViewById(R.id.usernameTextView)
        profileHomeButton = findViewById(R.id.profileHomeButton)

        val loggedInUsername = intent.getStringExtra("loggedInUsername")
        val loggedInEmail = intent.getStringExtra("loggedInEmail")

        usernameTextView.text = loggedInUsername ?: "Guest"

        profileHomeButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("loggedInUsername", loggedInUsername)
            intent.putExtra("loggedInEmail", loggedInEmail)
            startActivity(intent)
        }

        cartButton.setOnClickListener {
            val intent = Intent(this, CheckOutActivity::class.java)
            startActivity(intent)
        }
    }

    fun onCategoryListClicked(view: View) {
        val category = view.tag as String // Get the tag of the clicked category
        val intent = Intent(this, CategoryActivity::class.java)
        intent.putExtra("categoryName", category) // Optional: Pass category data
        startActivity(intent)
    }

    fun onProductClicked(view: View) {
        val productId = view.tag as String // Get the tag of the clicked view
        val intent = Intent(this, ProductDetailsActivity::class.java)
        intent.putExtra("productId", productId)
        startActivity(intent)
    }
}