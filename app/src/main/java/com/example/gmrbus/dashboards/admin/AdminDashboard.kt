package com.example.gmrbus.dashboards.admin

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.gmrbus.R
import com.example.gmrbus.models.Users
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class AdminDashboard : AppCompatActivity() {
    private lateinit var user: Users
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var uid: String
    lateinit var coordinatorName: TextView
    lateinit var studentData: Button
    private lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        title = "Dashboard"

        studentData = findViewById(R.id.btnStuData)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

        coordinatorName = findViewById(R.id.txtAdminName)

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()

        databaseReference = FirebaseDatabase.getInstance().getReference("Admins")
        if (uid.isNotEmpty()) {
            getUserData()
        }

        studentData.setOnClickListener {
            startActivity(Intent(this@AdminDashboard,StudentDataActivity::class.java))
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
                user = snapshot.getValue(Users::class.java)!!
                coordinatorName.text = user.getUsername()

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@AdminDashboard, "Failed to fetch Username", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}