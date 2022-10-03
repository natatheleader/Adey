package com.redemption.derama.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.redemption.derama.Model.Drama
import com.redemption.derama.Model.Season
import com.redemption.derama.R

class SeasonAdapter (private val data: List<Season>) : RecyclerView.Adapter<SeasonAdapter.MyViewHolder>()  {

    class MyViewHolder(val view: View): RecyclerView.ViewHolder(view){

        fun bind(property: Season){
            val title = view.findViewById<TextView>(R.id.seasonTitle)
            val imageView = view.findViewById<ImageView>(R.id.seasonImage)
            val description = view.findViewById<TextView>(R.id.seasonDescription)

            Log.d("data", property.toString())

            title.text = property.title
            description.text = property.description

//            Glide.with(view.context).load(property.image).centerCrop().into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.season_item, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
    }

}