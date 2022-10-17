package com.example.gmrbus

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class StudentDashboard : AppCompatActivity() {
    lateinit var textWelcome:TextView
    lateinit var textStuName:TextView
    lateinit var btnScan:Button
    lateinit var btnTrack:Button
    lateinit var btnBooking:Button
    lateinit var btnData:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_dashboard)

        title = "Dashboard"

        textWelcome = findViewById(R.id.txtWelcome)
        textStuName = findViewById(R.id.txtStudentName)
        btnScan = findViewById(R.id.btnScan)
        btnTrack = findViewById(R.id.btnTrack)
        btnBooking = findViewById(R.id.btnPayment)
        btnData = findViewById(R.id.btnDetails)


        btnScan.setOnClickListener {
            startActivity(Intent(this,ScannerActivity::class.java))
        }

        btnTrack.setOnClickListener {
            Toast.makeText(this,"Coming Soon",Toast.LENGTH_SHORT).show()
        }

        btnBooking.setOnClickListener {
            val openURL = Intent(android.content.Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://gmrit.edu.in/payments/")
            startActivity(openURL)
        }

        btnData.setOnClickListener {
            startActivity(Intent(this,BusInfoActivity::class.java))
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.dashboard,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.settings -> Toast.makeText(this,"Coming soon",Toast.LENGTH_SHORT).show()
            R.id.logOut -> onSupportNavigateUp()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}