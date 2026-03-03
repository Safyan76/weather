package com.example.weather

import android.graphics.drawable.Icon
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val datalist: ArrayList<DataClass>): RecyclerView.Adapter<Adapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.ViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.forecast_item, parent, false)
        return ViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: Adapter.ViewHolder, position: Int) {
        val currentItem = datalist[position]
        holder.rvIcons.setImageResource(currentItem.dataIcons)
        holder.rvDays.text = currentItem.dataDays
        holder.rvTemperature.text = currentItem.dataTemperature
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val rvIcons: ImageView = itemView.findViewById<ImageView>(R.id.weather_icon)
        val rvDays: TextView = itemView.findViewById<TextView>(R.id.day_of_week)
        val rvTemperature: TextView = itemView.findViewById<TextView>(R.id.temperature)
    }
}