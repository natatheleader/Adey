package com.redemption.derama

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.redemption.derama.Adapter.EpisodeAdapter
import com.redemption.derama.Interface.Api
import com.redemption.derama.Model.EpisodeViewItemModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.redemption.derama.Model.Episode

class Episode : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: RecyclerView.Adapter<*>

    private var offSet = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episode)
//        var adRequest = AdRequest.Builder().build()
//        RewardedAd.load(this,"ca-app-pub-3940256099942544/5224354917", adRequest, object : RewardedAdLoadCallback() {
//            override fun onAdFailedToLoad(adError: LoadAdError) {
//                adError?.toString()?.let { Log.d(TAG, it) }
//                mRewardedAd = null
//            }
//
//            override fun onAdLoaded(rewardedAd: RewardedAd) {
//                Log.d(TAG, "Ad was loaded.")
//                mRewardedAd = rewardedAd
//            }
//        })

        manager = LinearLayoutManager(this)

        val seasonId: String = intent.getStringExtra("SeasonId").toString()

        getAllEpisodeData(seasonId)
    }

    fun getAllEpisodeData(id: String){
        Api.retrofitService.getAllDataEpisode(id).enqueue(object: Callback<List<Episode>> {
            override fun onResponse(
                call: Call<List<Episode>>,
                response: Response<List<Episode>>
            ) {
                if(response.isSuccessful){
                    val data = ArrayList<EpisodeViewItemModel>()

                    for(item in response.body()!!){
                        if(offSet != 0 && offSet % 3 == 0) {
                            data.add(EpisodeViewItemModel(null,true))
                        }
                        offSet += 1
                        data.add(EpisodeViewItemModel(Episode(item.id, item.episode, item.title, item.description, item.link, item.thumb, item.season_id),false))
                    }

                    recyclerView = findViewById<RecyclerView>(R.id.episodeRecycler).apply{

                        myAdapter = EpisodeAdapter(data, EpisodeAdapter.OnClickListener {
                                position->val intent = Intent(this@Episode, Player::class.java)
                            intent.putExtra("link", data[position].episode?.link)
                            startActivity(intent)
                        })
                        layoutManager = manager
                        adapter = myAdapter
                    }
                }
            }

            override fun onFailure(call: Call<List<Episode>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}