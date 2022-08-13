package com.redemption.adey

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.redemption.adey.Adapter.CustomAdapter
import com.redemption.adey.Interface.YoutubeService
import com.redemption.adey.Model.ItemViewModel
import com.redemption.adey.Model.ItemsViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.episode_list)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
//        for (i in 1..20) {
//            data.add(ItemsViewModel(R.drawable.ic_baseline_folder_24, "Item " + i, "Item Duration" + i))
//        }

        //test network call
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val youtubeService: YoutubeService = retrofit.create(YoutubeService::class.java)

        youtubeService.getPlayList().enqueue(object : Callback<ItemViewModel> {
            override fun onResponse(call: Call<ItemViewModel>, response: Response<ItemViewModel>) {
                Log.i("MainActivity", response.toString())

                if(!response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "Unsuccessfull network call", Toast.LENGTH_LONG).show()
                    return
                }

                val body = response.body()!!
                val item = body.items

                for (i in 0 until item.size) {
                    data.add(ItemsViewModel(R.drawable.ic_baseline_folder_24, item[i].snippet.title))
                }

                // This will pass the ArrayList to our Adapter
                val adapter = CustomAdapter(data)

                // Setting the Adapter with the recyclerview
                recyclerview.adapter = adapter
            }

            override fun onFailure(call: Call<ItemViewModel>, t: Throwable) {
                Log.i("MainActivity", t.message ?: "Null message")
            }

        })
    }
}