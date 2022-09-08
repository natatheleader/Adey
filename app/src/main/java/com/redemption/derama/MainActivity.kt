package com.redemption.derama

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.redemption.derama.Adapter.CustomAdapter
import com.redemption.derama.Model.ItemsViewModel
import com.redemption.derama.Model.ViewItemModel


class MainActivity : AppCompatActivity() {

    private var currentPage = 0
    private var totalAvailablePerPage = 0
    private var pageToken: String? = ""
    private var offSet = 0
    private var adapter: CustomAdapter? = null
    private val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this)[SharedViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.episode_list)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ViewItemModel>()

        viewModel.refreshPlaylist("")
        viewModel.playlistLiveData.observe(this) { response ->
            if (response == null) {
                Toast.makeText(this@MainActivity, "Unsuccessfull network call", Toast.LENGTH_LONG).show()
                return@observe
            }

            val items = response.items

            if(currentPage== 0){
//                for (item in items) {
//                    data.add(ItemsViewModel(item.snippet.thumbnails.high.url, item.snippet.title))
//                }


                for(item in items){

                    if(offSet!= 0 && offSet % 5 == 0) {
                        data.add(ViewItemModel(null,true))

                    }
                    offSet += 1
                    data.add(ViewItemModel(ItemsViewModel(item.snippet.thumbnails.high.url, item.snippet.title, item.snippet.description, item.snippet.resourceId.videoId),false))

                }
                totalAvailablePerPage = response.pageInfo.totalResults
                pageToken = response.nextPageToken

                adapter = CustomAdapter(data, CustomAdapter.OnClickListener{
                        position->val intent = Intent(this@MainActivity, Player::class.java)
                                    intent.putExtra("videoId", data[position].data?.link)
                                    intent.putExtra("description", data[position].data?.description)
                                    intent.putExtra("title", data[position].data?.title)
                                    startActivity(intent)
                })

                // Setting the Adapter with the recyclerview
                recyclerview.adapter = adapter
            }else{
                val newData = ArrayList<ViewItemModel>()

//                for (item in items) {
//                    newData.add(ItemsViewModel(item.snippet.thumbnails.high.url, item.snippet.title))
//                }
                for(item in items){

                    if(offSet!= 0 && offSet % 5 == 0) {
                        newData.add(ViewItemModel(null,true))

                    }
                    offSet += 1
                    newData.add(ViewItemModel(ItemsViewModel(item.snippet.thumbnails.high.url, item.snippet.title, item.snippet.description, item.snippet.resourceId.videoId),false))

                }
                totalAvailablePerPage = response.pageInfo.totalResults
                pageToken = response.nextPageToken
//                recyclerview.dispatchLayout()
                recyclerview.recycledViewPool.clear()
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