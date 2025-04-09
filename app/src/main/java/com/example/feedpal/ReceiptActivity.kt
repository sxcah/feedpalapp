package com.example.feedpal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text

class ReceiptActivity : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var orderNumberTextView: TextView
    private lateinit var orderDateTextView: TextView
    private lateinit var orderItemsContainer: LinearLayout
    private lateinit var receiptSubtotalTextView: TextView
    private lateinit var receiptShippingTextView: TextView
    private lateinit var receiptTotalTextView: TextView
    private lateinit var fullNameTextView: TextView // To display the full name
    private lateinit var addressTextView: TextView
    private lateinit var cityTextView: TextView
    private lateinit var stateRProvince: TextView
    private lateinit var zipPostalCodeTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt)

        // Initialize views
        backButton = findViewById(R.id.backButtonReceipt) // Assuming you have a back button in your receipt layout
        orderNumberTextView = findViewById(R.id.orderNumberTextView)
        orderDateTextView = findViewById(R.id.orderDateTextView)
        orderItemsContainer = findViewById(R.id.orderItemsContainer)
        receiptSubtotalTextView = findViewById(R.id.receiptSubtotalTextView)
        receiptShippingTextView = findViewById(R.id.receiptShippingTextView)
        receiptTotalTextView = findViewById(R.id.receiptTotalTextView)
        // Initialize the TextView to display the full name
        // Make sure you add an appropriate TextView with this ID in your activity_receipt.xml
        fullNameTextView = findViewById(R.id.fullNameTextView)
        addressTextView = findViewById(R.id.addressTextView)
        cityTextView = findViewById(R.id.cityTextView)
        stateRProvince = findViewById(R.id.stateProvinceTextView) // Assuming this ID in your XML
        zipPostalCodeTextView = findViewById(R.id.zipPostalCodeTextView)

        // Retrieve data passed from the previous activity
        val intent = getIntent()
        val fullName = intent.getStringExtra("fullname")
        val streetAddress = intent.getStringExtra("streetAddress")
        val city = intent.getStringExtra("city")
        val stateProvince = intent.getStringExtra("stateProvince")
        val zipPostalCode = intent.getStringExtra("zipPostalCode")
        // Retrieve order items and total amount if you passed them
        // val orderItems = intent.getSerializableExtra("orderItems") as? ArrayList<OrderItem>
        // val totalAmount = intent.getStringExtra("totalAmount")

        // Display the retrieved data
        fullNameTextView.text = fullName
        addressTextView.text = streetAddress
        cityTextView.text = city
        stateRProvince.text = stateProvince
        zipPostalCodeTextView.text = zipPostalCode

        // For demonstration purposes, let's set some static order details
        orderNumberTextView.text = "#${generateOrderNumber()}"
        orderDateTextView.text = getCurrentDate()
        receiptSubtotalTextView.text = "₱ 246.90" // Replace with actual subtotal
        receiptShippingTextView.text = "₱ 15.00"   // Replace with actual shipping cost
        receiptTotalTextView.text = "₱ 261.90"     // Replace with actual total amount

        // Example of how to dynamically add order items to the layout
        // if (orderItems != null) {
        //     for (item in orderItems) {
        //         val itemView = layoutInflater.inflate(R.layout.item_receipt_order, orderItemsContainer, false)
        //         val itemNameTextView = itemView.findViewById<TextView>(R.id.itemNameTextView)
        //         val itemPriceTextView = itemView.findViewById<TextView>(R.id.itemPriceTextView)
        //         val itemQuantityTextView = itemView.findViewById<TextView>(R.id.itemQuantityTextView)
        //
        //         itemNameTextView.text = item.name
        //         itemPriceTextView.text = "₱ ${String.format("%.2f", item.price)}"
        //         itemQuantityTextView.text = "x ${item.quantity}"
        //
        //         orderItemsContainer.addView(itemView)
        //     }
        // }

        backButton.setOnClickListener {
            val intent = Intent(this, HomePageActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish() // Optionally finish ReceiptActivity
        }
    }

    private fun generateOrderNumber(): String {
        // Implement logic to generate a unique order number
        val timestamp = System.currentTimeMillis()
        return timestamp.toString().takeLast(8)
    }

    private fun getCurrentDate(): String {
        val calendar = java.util.Calendar.getInstance()
        val year = calendar.get(java.util.Calendar.YEAR)
        val month = calendar.get(java.util.Calendar.MONTH) + 1 // Month is 0-indexed
        val day = calendar.get(java.util.Calendar.DAY_OF_MONTH)
        return "$year-$month-$day" // You can format it as needed
    }
}