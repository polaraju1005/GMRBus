package com.example.gmrbus.dashboards.student

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.gmrbus.R
import com.example.gmrbus.gateway.activity.InitialScreenActivity
import com.example.gmrbus.models.Students
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class StudentDashboard : AppCompatActivity() {
    private lateinit var users: Students
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var uid: String
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var textWelcome: TextView
    lateinit var textStuName: TextView
    lateinit var btnScan: Button
    private lateinit var btnTrack: Button
    private lateinit var btnBooking: Button
    lateinit var btnData: Button
    lateinit var navUserName: TextView
    lateinit var navStudentHeader: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_dashboard)

        title = "Dashboard"

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayoutStu)
        val navView: NavigationView = findViewById(R.id.nav_view_stu)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        textWelcome = findViewById(R.id.txtWelcome)
        textStuName = findViewById(R.id.txtStudentName)
        btnScan = findViewById(R.id.btnScan)
        btnTrack = findViewById(R.id.btnTrack)
        btnBooking = findViewById(R.id.btnPayment)
        btnData = findViewById(R.id.btnDetails)
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()

//        navStudentHeader =
//        navUserName = navView.findViewById(R.id.user_name)

//        navUserName.text = "Sai"

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> Toast.makeText(
                    applicationContext,
                    "Clicked Home",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.busRoutes -> Toast.makeText(
                    applicationContext,
                    "Clicked Bus Routes",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.nav_settings -> Toast.makeText(
                    applicationContext,
                    "Clicked settings",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.nav_logout -> Toast.makeText(
                    applicationContext,
                    "Clicked logout",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.nav_share -> Toast.makeText(
                    applicationContext,
                    "Clicked share",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.nav_rate_us -> Toast.makeText(
                    applicationContext,
                    "Clicked rate us",
                    Toast.LENGTH_SHORT
                ).show()
            }
            true
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        if (uid.isNotEmpty()) {
            getUserData()
        }

        btnScan.setOnClickListener {
            startActivity(Intent(this, ScannerActivity::class.java))
        }

        btnTrack.setOnClickListener {
            Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
        }

        btnBooking.setOnClickListener {
            startActivity(Intent(this, InitialScreenActivity::class.java))
        }

        btnData.setOnClickListener {
            startActivity(Intent(this, BusInfoActivity::class.java))
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getUserData() {
        databaseReference.child(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                users = snapshot.getValue(Students::class.java)!!
                textStuName.text = users.getUsername()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@StudentDashboard,
                    "Failed to fetch username",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}