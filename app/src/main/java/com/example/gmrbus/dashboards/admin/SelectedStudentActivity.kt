package com.example.gmrbus.dashboards.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.gmrbus.R
import org.w3c.dom.Text

class SelectedStudentActivity : AppCompatActivity() {
//    lateinit var studentName:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_student)

   val name = intent.getStringExtra("name1")
//
    supportActionBar?.title = name
//        studentName = findViewById(R.id.studentName)
//
//        var intent = intent.extras
//
//        var stuName = intent!!.getString("name")
//        studentName.text = stuName
    }
}