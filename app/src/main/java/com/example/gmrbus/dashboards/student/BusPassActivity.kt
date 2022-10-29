package com.example.gmrbus.dashboards.student

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.gmrbus.R

class BusPassActivity : AppCompatActivity() {
    lateinit var downloadBusPass:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_pass)

        downloadBusPass = findViewById(R.id.btnBusPassDownload)

        downloadBusPass.setOnClickListener {
            Toast.makeText(this,"Downloading...",Toast.LENGTH_SHORT).show()
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}