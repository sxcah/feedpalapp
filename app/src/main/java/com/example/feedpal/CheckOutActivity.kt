package com.example.feedpal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CheckOutActivity : AppCompatActivity() {

    private lateinit var homeButton: LinearLayout
    private lateinit var placeOrderButton: Button

    // Add references to the EditText fields
    private lateinit var fullNameEditText: EditText
    private lateinit var streetAddressEditText: EditText
    private lateinit var cityEditText: EditText
    private lateinit var stateProvinceEditText: EditText
    private lateinit var zipPostalCodeEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)

        // Initialize views
        val bottomNavigationView = findViewById<LinearLayout>(R.id.bottomNavigationView)
        homeButton = bottomNavigationView.findViewById(R.id.homeButton)
        placeOrderButton = findViewById(R.id.placeOrderButton)

        // Initialize EditText fields
        fullNameEditText = findViewById(R.id.fullNameEditText)
        streetAddressEditText = findViewById(R.id.streetAddressEditText)
        cityEditText = findViewById(R.id.cityEditText)
        stateProvinceEditText = findViewById(R.id.stateProvinceEditText)
        zipPostalCodeEditText = findViewById(R.id.zipPostalCodeEditText)

        // Set up listener for the Home button to go back to HomePageActivity
        homeButton.setOnClickListener {
            val intent = Intent(this, HomePageActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }

        // Set up listener for the Place Order button to go to ReceiptActivity
        placeOrderButton.setOnClickListener {
            // You can retrieve the text from the EditText fields here if needed
            validateTextField()
        }
    }

    fun validateTextField() {
        val fullName = fullNameEditText.text.toString()
        val streetAddress = streetAddressEditText.text.toString()
        val city = cityEditText.text.toString()
        val stateProvince = stateProvinceEditText.text.toString()
        val zipPostalCode = zipPostalCodeEditText.text.toString()

        if (fullName.isNotEmpty() && streetAddress.isNotEmpty() && city.isNotEmpty() && stateProvince.isNotEmpty() && zipPostalCode.isNotEmpty()) {
            val intent = Intent(this, ReceiptActivity::class.java)
            intent.putExtra("fullname", fullName)
            intent.putExtra("streetAddress", streetAddress)
            intent.putExtra("city", city)
            intent.putExtra("stateProvince", stateProvince)
            intent.putExtra("zipPostalCode", zipPostalCode)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
        }
    }

}