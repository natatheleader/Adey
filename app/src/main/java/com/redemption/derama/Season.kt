package com.redemption.derama

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.redemption.derama.Adapter.SeasonAdapter
import com.redemption.derama.Interface.Api
import com.redemption.derama.Model.Season
import com.redemption.derama.Model.SeasonViewItemModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Season : AppCompatActivity() {

    private var mRewardedAd: RewardedAd? = null
    private final var TAG = "Season"

    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: RecyclerView.Adapter<*>

    private var offSet = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_season)

        MobileAds.initialize(this@Season) {}

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
                        if(offSet != 0 && offSet % 3 == 0) {
                            data.add(SeasonViewItemModel(null,true))
                        }
                        offSet += 1
                        data.add(SeasonViewItemModel(Season(item.id, item.number, item.drama_id),false))
                    }

                    recyclerView = findViewById<RecyclerView>(R.id.seasonRecycler).apply{
                        myAdapter = SeasonAdapter(data, SeasonAdapter.OnClickListener {
                            position->
                            run {

                                AdLoad(data[position].season?.id.toString())

                                if (mRewardedAd != null) {
                                    mRewardedAd?.show(this@Season, OnUserEarnedRewardListener() {
                                        fun onUserEarnedReward(rewardItem: RewardItem) {
                                        }
                                    })
                                } else {
                                    intent = Intent(this@Season, Episode::class.java)
                                    intent.putExtra("SeasonId", data[position].season?.id.toString())
                                    startActivity(intent)
                                }
                            }
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

    fun AdLoad(id: String) {
        var adRequest = AdRequest.Builder().build()
        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917", adRequest, object : RewardedAdLoadCallback() {

            override fun onAdFailedToLoad(adError: LoadAdError) {
                adError?.toString()?.let { Log.d(TAG, it) }
                mRewardedAd = null
            }

            override fun onAdLoaded(rewardedAd: RewardedAd) {
                mRewardedAd = rewardedAd

                mRewardedAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                    override fun onAdClicked() {
                        // Called when a click is recorded for an ad.
                        Log.d(TAG, "Ad was clicked.")
                    }

                    override fun onAdDismissedFullScreenContent() {
                        // Called when ad is dismissed.
                        // Set the ad reference to null so you don't show the ad a second time.
                        intent = Intent(this@Season, Episode::class.java)
                        intent.putExtra("SeasonId", id)
                        startActivity(intent)
                        mRewardedAd = null
                    }

                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                        // Called when ad fails to show.
                        intent = Intent(this@Season, Episode::class.java)
                        intent.putExtra("SeasonId", id)
                        startActivity(intent)
                        mRewardedAd = null
                    }

                    override fun onAdImpression() {
                        // Called when an impression is recorded for an ad.
                        Log.d(TAG, "Ad recorded an impression.")
                    }

                    override fun onAdShowedFullScreenContent() {
                        // Called when ad is shown.
                        Log.d(TAG, "Ad showed fullscreen content.")
                    }
                }
            }
        })
    }
}