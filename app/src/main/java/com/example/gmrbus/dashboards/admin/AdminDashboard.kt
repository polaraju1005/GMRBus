package com.example.gmrbus.dashboards.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.gmrbus.R
import com.example.gmrbus.models.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class AdminDashboard : AppCompatActivity() {
    private lateinit var user:Users
    private lateinit var auth:FirebaseAuth
    private lateinit var databaseReference:DatabaseReference
    private lateinit var uid:String
    lateinit var coordinatorName:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        title = "Dashboard"

        coordinatorName = findViewById(R.id.txtAdminName)

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()

        databaseReference = FirebaseDatabase.getInstance().getReference("Admins")
        if (uid.isNotEmpty()){
            getUserData()
        }
    }

    private fun getUserData() {
        databaseReference.child(uid).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                user = snapshot.getValue(Users::class.java)!!
                coordinatorName.text = user.getUsername()

            }

            override fun onCancelled(error: DatabaseError) {
               Toast.makeText(this@AdminDashboard,"Failed to fetch Username",Toast.LENGTH_SHORT).show()

            }


        })
    }
}