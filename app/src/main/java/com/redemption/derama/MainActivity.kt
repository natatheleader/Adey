package com.redemption.derama

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.redemption.derama.Adapter.DramaAdapter
import com.redemption.derama.Interface.Api
import com.redemption.derama.Model.Drama
import com.redemption.derama.Model.ViewItemModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: RecyclerView.Adapter<*>

    private var offSet = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = LinearLayoutManager(this)
        getAllData()
    }

    fun getAllData(){
        Api.retrofitService.getAllData().enqueue(object: Callback<List<Drama>>{
            override fun onResponse(
                call: Call<List<Drama>>,
                response: Response<List<Drama>>
            ) {
                if(response.isSuccessful){
                    val data = ArrayList<ViewItemModel>()

                    for(item in response.body()!!){
                        if(offSet != 0 && offSet % 5 == 0) {
                            data.add(ViewItemModel(null,true))
                        }
                        offSet += 1
                        data.add(ViewItemModel(Drama(item.id, item.title, item.description, item.thumb),false))
                    }

                    recyclerView = findViewById<RecyclerView>(R.id.dramaRecycler).apply{

                        myAdapter = DramaAdapter(data, DramaAdapter.OnClickListener {
                            position->val intent = Intent(this@MainActivity, Season::class.java)
                            intent.putExtra("DramaId", data[position].drama?.id.toString())
                            startActivity(intent)
                        })
                        layoutManager = manager
                        adapter = myAdapter
                    }
                }
            }

            override fun onFailure(call: Call<List<Drama>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}