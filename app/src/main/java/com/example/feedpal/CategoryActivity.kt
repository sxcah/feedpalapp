package com.example.feedpal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CategoryActivity : AppCompatActivity() {

    private lateinit var categoriesContainerName: TextView
    private lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        categoriesContainerName = findViewById(R.id.categoriesContainerName)
        backButton = findViewById(R.id.backButtonCategory)

        backButton.setOnClickListener() {
            onBackPressed()
        }

        // Get the category name passed from the previous activity
        val categoryName = intent.getStringExtra("categoryName")

        // Update the TextView if a category name was received
        if (!categoryName.isNullOrEmpty()) {
            categoriesContainerName.text = categoryName
        } else {
            // Optional: Set a default title if no category name is passed
            categoriesContainerName.text = "Categories"
        }
    }

    fun onProductClicked(view: View) {
        val productId = view.tag as String // Get the tag of the clicked view
        val intent = Intent(this, ProductDetailsActivity::class.java)
        intent.putExtra("productId", productId)
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}