package com.example.gmrbus.dashboards.student

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gmrbus.R
import com.example.gmrbus.adapters.BusListRecyclerAdapter
import com.example.gmrbus.models.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class BusInfoActivity : AppCompatActivity() {
    private lateinit var busList: ArrayList<Users>
    lateinit var itemList:RecyclerView
    lateinit var layoutManager:RecyclerView.LayoutManager
    private lateinit var adapter:BusListRecyclerAdapter
    lateinit var auth: FirebaseAuth
    lateinit var mDbRef:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_info)

        title = "Bus Info"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        auth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().reference

        busList = ArrayList()
        adapter = BusListRecyclerAdapter(this,busList)

        itemList = findViewById(R.id.bus_list_recyclerView)
        itemList.layoutManager = LinearLayoutManager(this)
        itemList.adapter = adapter

        mDbRef.child("Admins").addValueEventListener(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                busList.clear()
                for (postSnapShot in snapshot.children){
                    val currentUser = postSnapShot.getValue(Users::class.java)

                    if (auth.currentUser?.uid!=currentUser?.getUID()){
                        busList.add(currentUser!!)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}