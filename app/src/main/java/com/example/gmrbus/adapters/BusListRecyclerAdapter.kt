package com.example.gmrbus.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gmrbus.R
import com.example.gmrbus.dashboards.student.BusDetailsActivity
import com.example.gmrbus.dashboards.student.BusInfoActivity
import com.example.gmrbus.models.Users

class BusListRecyclerAdapter(val context: BusInfoActivity, val busList: ArrayList<Users>) :
    RecyclerView.Adapter<BusListRecyclerAdapter.HomeViewHolder>() {
    class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var busNumber = itemView.findViewById<TextView>(R.id.txtBusNumber)
        var adminMail = itemView.findViewById<TextView>(R.id.txtAdminMail)
        var adminNumber = itemView.findViewById<TextView>(R.id.txtAdminNumber)
        val stack = itemView.findViewById<RelativeLayout>(R.id.busRecyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.bus_list_recycler_view, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val currentUser: Users = busList[position]

        holder.busNumber.text = currentUser.getBusNumber()
        holder.adminMail.text = currentUser.getEmail()
        holder.adminNumber.text = currentUser.getPhone()

        holder.itemView.setOnClickListener {
            val i = Intent(context, BusDetailsActivity::class.java)

            i.putExtra("name",currentUser.getUsername())

            context.startActivity(i)
        }
    }


    override fun getItemCount(): Int {
        return busList.size
    }
}