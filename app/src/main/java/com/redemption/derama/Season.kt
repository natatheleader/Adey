package com.redemption.derama

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.redemption.derama.Adapter.SeasonAdapter
import com.redemption.derama.Interface.Api
import com.redemption.derama.Model.Season
import com.redemption.derama.Model.SeasonViewItemModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Season : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: RecyclerView.Adapter<*>

    private var offSet = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_season)

        manager = LinearLayoutManager(this)

        val dramaId: String = intent.getStringExtra("DramaId").toString()

        getAllData(dramaId)
    }

    private fun getAllData(id: String){
        Api.retrofitService.getAllDataSeason(id).enqueue(object: Callback<List<Season>> {
            override fun onResponse(
                call: Call<List<Season>>,
                response: Response<List<Season>>
            ) {
                if(response.isSuccessful){
                    val data = ArrayList<SeasonViewItemModel>()

                    for(item in response.body()!!){
                        if(offSet != 0 && offSet % 2 == 0) {
                            data.add(SeasonViewItemModel(null,true))
                        }
                        offSet += 1
                        data.add(SeasonViewItemModel(Season(item.id, item.number, item.drama_id),false))
                    }

                    recyclerView = findViewById<RecyclerView>(R.id.seasonRecycler).apply{
                        myAdapter = SeasonAdapter(data, SeasonAdapter.OnClickListener {
                            position->val intent = Intent(this@Season, Episode::class.java)
                            intent.putExtra("SeasonId", data[position].season?.id.toString())
                            startActivity(intent)
                        })
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