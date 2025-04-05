package com.example.feedpal

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class HomePageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // You can add logic here to fetch user data, categories, best sellers, etc.
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