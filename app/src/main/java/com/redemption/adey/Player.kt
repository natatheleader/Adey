package com.redemption.adey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Player : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        val link: TextView = findViewById(R.id.link)

        val bundle: Bundle? = intent.extras
        val videoId: String? = intent.getString("videoId")
//        val myArray: ArrayList<String>? = intent.getStringArrayList("myArray")

        link.setText(videoId)
    }
}