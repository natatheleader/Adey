package com.redemption.derama.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.redemption.derama.Model.Drama
import com.redemption.derama.R

class DramaAdapter (private val data: List<Drama>) : RecyclerView.Adapter<DramaAdapter.MyViewHolder>()  {

    class MyViewHolder(val view: View): RecyclerView.ViewHolder(view){

        fun bind(property: Drama){
            val title = view.findViewById<TextView>(R.id.dramaTitle)
            val imageView = view.findViewById<ImageView>(R.id.dramaImage)
            val description = view.findViewById<TextView>(R.id.dramaDescription)

            Log.d("data", property.toString())

            title.text = property.title
            description.text = property.description

//            Glide.with(view.context).load(property.image).centerCrop().into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.drama_item, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
    }

}