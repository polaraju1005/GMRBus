package com.example.gmrbus.dashboards.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gmrbus.R
import com.example.gmrbus.adapters.StudentListRecyclerAdapter
import com.example.gmrbus.models.Students
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class StudentDataActivity : AppCompatActivity() {
    private lateinit var stuList: ArrayList<Students>
    lateinit var itemList:RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: StudentListRecyclerAdapter
    lateinit var auth: FirebaseAuth
    lateinit var mDbRef:DatabaseReference
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_data)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().reference

        stuList = ArrayList()
        adapter = StudentListRecyclerAdapter(this,stuList)

//        toolbar = findViewById(R.id.toolBar)
//        this.setSupportActionBar(toolbar)
//        this.supportActionBar!!.title = ""

        itemList = findViewById(R.id.stuRecyclerView)
        itemList.layoutManager = LinearLayoutManager(this)
        itemList.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        itemList.adapter = adapter

        mDbRef.child("Users").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                stuList.clear()
                for (postSnapShot in snapshot.children){
                    val currentUser = postSnapShot.getValue(Students::class.java)

                    if (auth.currentUser?.uid!=currentUser?.getUID()){
                        stuList.add(currentUser!!)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

}