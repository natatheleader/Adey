package com.redemption.adey

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.redemption.adey.Adapter.CustomAdapter
import com.redemption.adey.Model.ItemsViewModel


class MainActivity : AppCompatActivity() {

    private var currentPage = 0
    private var totalAvailablePerPage = 0
    private var pageToken: String? = ""
    private var adapter: CustomAdapter? = null;
    private val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this).get(SharedViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.episode_list)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()

        viewModel.refreshPlaylist("")
        viewModel.playlistLiveData.observe(this) { response ->
            if (response == null) {
                Toast.makeText(this@MainActivity, "Unsuccessfull network call", Toast.LENGTH_LONG).show()
                return@observe
            }

            val items = response.items

            if(currentPage== 0){
                for (item in items) {
                    data.add(ItemsViewModel(item.snippet.thumbnails.high.url, item.snippet.title))
                }

                totalAvailablePerPage = response.pageInfo.totalResults
                pageToken = response.nextPageToken

                adapter = CustomAdapter(data)

                // Setting the Adapter with the recyclerview
                recyclerview.adapter = adapter
            }else{
                val newData = ArrayList<ItemsViewModel>()

                for (item in items) {
                    newData.add(ItemsViewModel(item.snippet.thumbnails.high.url, item.snippet.title))
                }

                totalAvailablePerPage = response.pageInfo.totalResults
                pageToken = response.nextPageToken
                adapter!!.updateList(newData, adapter!!.itemCount)



            }
        }

        recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerview.canScrollVertically(1)) {
                    currentPage += 1
                    if (currentPage <= totalAvailablePerPage / 10) {
                        viewModel.refreshPlaylist(pageToken!!)
                    }
                }
            }
        })
    }
}