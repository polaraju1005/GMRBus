package com.example.gmrbus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AdminDashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        title = "Dashboard"
    }
}