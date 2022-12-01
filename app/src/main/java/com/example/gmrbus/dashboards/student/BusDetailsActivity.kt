package com.example.gmrbus.dashboards.student

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gmrbus.R

class BusDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_details)

        val name = intent.getStringExtra("name")

        supportActionBar?.title = name

    }
}