package com.redemption.derama

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.redemption.derama.Adapter.DramaAdapter
import com.redemption.derama.Adapter.SeasonAdapter
import com.redemption.derama.Interface.Api
import com.redemption.derama.Interface.ApiSeason
import com.redemption.derama.Model.Season
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Season : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_season)

        manager = LinearLayoutManager(this)
        getAllData()
    }

    fun getAllData(){
        ApiSeason.retrofitService.getAllSeasonData().enqueue(object: Callback<List<Season>> {
            override fun onResponse(
                call: Call<List<Season>>,
                response: Response<List<Season>>
            ) {
                if(response.isSuccessful){
                    recyclerView = findViewById<RecyclerView>(R.id.seasonRecycler).apply{
                        myAdapter = SeasonAdapter(response.body()!!)
                        layoutManager = manager
                        adapter = myAdapter
                    }
                }
            }

            override fun onFailure(call: Call<List<Season>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}