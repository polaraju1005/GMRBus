package com.example.gmrbus.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.gmrbus.R
import com.example.gmrbus.dashboards.admin.SelectedStudentActivity
import com.example.gmrbus.dashboards.admin.StudentDataActivity
import com.example.gmrbus.models.Students

class StudentListRecyclerAdapter(
    val context: StudentDataActivity,
    val stuList: ArrayList<Students>
) : RecyclerView.Adapter<StudentListRecyclerAdapter.HomeViewHolder>() {
    class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var stuUserName = itemView.findViewById<TextView>(R.id.txtStuName)
        var prefix = itemView.findViewById<TextView>(R.id.txtPrefix)
        val stack = itemView.findViewById<ConstraintLayout>(R.id.stu_recycler_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.row_students, parent, false)
        return HomeViewHolder(view)

    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val currentUser: Students = stuList[position]

        holder.stuUserName.text = currentUser.getUsername()
        holder.prefix.text = currentUser.getUsername()?.substring(0, 1)

        holder.itemView.setOnClickListener {
            val i = Intent(context, SelectedStudentActivity::class.java)

            i.putExtra("name1", currentUser.getUsername())

            context.startActivity(i)
        }

    }

    override fun getItemCount(): Int {
        return stuList.size
    }
}