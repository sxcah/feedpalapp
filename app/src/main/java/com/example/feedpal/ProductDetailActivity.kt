package com.example.feedpal

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var productNameTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var ratingTextView: TextView
    private lateinit var reviewTextView: TextView
    private lateinit var ratingPercentTextView: TextView
    private lateinit var backButton: ImageView
    private lateinit var addToCartButton: Button
    private var isItemInCart = false
    // Add other views as needed

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        productNameTextView = findViewById(R.id.productNameTextView)
        descriptionTextView = findViewById(R.id.descriptionTextView)
        ratingTextView = findViewById(R.id.ratingTextView)
        reviewTextView = findViewById(R.id.reviewTextView)
        ratingPercentTextView = findViewById(R.id.ratingPercentTextView)
        addToCartButton = findViewById(R.id.addToCartButton)
        backButton = findViewById(R.id.backButton)
        // Initialize other views

        // Set OnClickListener for the back button
        backButton.setOnClickListener {
            onBackPressed() // This method handles the back navigation
        }

        addToCartButton.setOnClickListener {
            isItemInCart = !isItemInCart

            if (isItemInCart) {
                addToCartButton.text = "Remove from Cart"
                val cartDrawable = ContextCompat.getDrawable(this, R.drawable.ic_trash)
                addToCartButton.setCompoundDrawablesWithIntrinsicBounds(null, null, cartDrawable, null)
                addToCartButton.backgroundTintList = ContextCompat.getColorStateList(this, R.color.red)
            } else {
                addToCartButton.text = "Add to Cart"
                val cartDrawable = ContextCompat.getDrawable(this, R.drawable.ic_cart)
                addToCartButton.setCompoundDrawablesWithIntrinsicBounds(null, null, cartDrawable, null)
                addToCartButton.backgroundTintList = ContextCompat.getColorStateList(this, R.color.light_green)
            }
        }

        // Retrieve the product ID from the Intent
        val productId = intent.getStringExtra("productId")

        // Use the productId to fetch and display the correct product details
        when (productId) {
            "cooked_lean_meats" -> {
                ratingTextView.text = "4.9"
                reviewTextView.text = "(649 reviews)"
                ratingPercentTextView.text = "97%"
                productNameTextView.text = "Cooked Lean Meats"
                descriptionTextView.text = "Plain, cooked chicken, turkey, or lean beef provide excellent protein. Ensure they are boneless and unseasoned to avoid digestive upset."
                // Set other details like price, rating, image, etc.
            }
            "high_quality_kibble" -> {
                ratingTextView.text = "4.7"
                reviewTextView.text = "(649 reviews)"
                ratingPercentTextView.text = "96%"
                productNameTextView.text = "High Quality Kibble"
                descriptionTextView.text = "A premium blend of essential nutrients for your dog's optimal health and energy."
                // Set other details for kibble
            }
            // Add cases for other product IDs
            else -> {
                // Handle the case where the product ID is not recognized
                productNameTextView.text = "Product Not Found"
                descriptionTextView.text = "Sorry, the details for this product could not be loaded."
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed() // This will finish the current activity and go back to the previous one
    }
}