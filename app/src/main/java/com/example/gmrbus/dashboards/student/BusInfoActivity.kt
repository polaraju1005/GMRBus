package com.example.gmrbus.dashboards.student

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gmrbus.R

class BusInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_info)

        title = "Bus Info"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}