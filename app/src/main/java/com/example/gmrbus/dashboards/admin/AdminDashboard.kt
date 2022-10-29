package com.example.gmrbus.dashboards.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gmrbus.R

class AdminDashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        title = "Dashboard"
    }
}