package com.redemption.derama

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.redemption.derama.Adapter.DramaAdapter
import com.redemption.derama.Interface.Api
import com.redemption.derama.Model.Drama
import com.redemption.derama.Model.ViewItemModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var mRewardedAd: RewardedAd? = null
    private final var TAG = "MainActivity"

    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: RecyclerView.Adapter<*>

    private var offSet = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this@MainActivity) {}

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
                        if(offSet != 0 && offSet % 3 == 0) {
                            data.add(ViewItemModel(null,true))
                        }
                        offSet += 1
                        data.add(ViewItemModel(Drama(item.id, item.title, item.description, item.thumb),false))
                    }

                    recyclerView = findViewById<RecyclerView>(R.id.dramaRecycler).apply{

                        myAdapter = DramaAdapter(data, DramaAdapter.OnClickListener {
                            position->
                            run {

                                AdLoad(data[position].drama?.id.toString(), )

                                if (mRewardedAd != null) {
                                    mRewardedAd?.show(this@MainActivity, OnUserEarnedRewardListener() {
                                            fun onUserEarnedReward(rewardItem: RewardItem) {
                                            }
                                        })
                                } else {
                                    intent = Intent(this@MainActivity, Season::class.java)
                                    intent.putExtra("DramaId", data[position].drama?.id.toString())
                                    startActivity(intent)
                                }
                            }
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

    fun AdLoad(id: String) {
        var adRequest = AdRequest.Builder().build()
        RewardedAd.load(this, this@MainActivity.getString(R.string.rewardedAdUnit), adRequest, object : RewardedAdLoadCallback() {

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
                        intent = Intent(this@MainActivity, Season::class.java)
                        intent.putExtra("DramaId", id)
                        startActivity(intent)
                        mRewardedAd = null
                    }

                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                        // Called when ad fails to show.
                        intent = Intent(this@MainActivity, Season::class.java)
                        intent.putExtra("DramaId", id)
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