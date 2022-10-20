package com.example.gmrbus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class BookingActivity : AppCompatActivity() {
    lateinit var booking: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        booking = findViewById(R.id.btnBooking)

        booking.setOnClickListener {
            Toast.makeText(this, "Your seat is booked", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, BusPassActivity::class.java))
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}