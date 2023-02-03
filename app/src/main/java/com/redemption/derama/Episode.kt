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
import com.redemption.derama.Adapter.EpisodeAdapter
import com.redemption.derama.Interface.Api
import com.redemption.derama.Model.Episode
import com.redemption.derama.Model.EpisodeViewItemModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Episode : AppCompatActivity() {

    private var mRewardedAd: RewardedAd? = null
    private final var TAG = "Episode"

    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: RecyclerView.Adapter<*>

    private var offSet = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episode)

        MobileAds.initialize(this@Episode) {}

        manager = LinearLayoutManager(this)

        val seasonId: String = intent.getStringExtra("SeasonId").toString()

        getAllEpisodeData(seasonId)
    }

    private fun getAllEpisodeData(id: String) {
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
                                position->
                            run{

                                AdLoad(data[position].episode?.link.toString())

                                if (mRewardedAd != null) {
                                    mRewardedAd?.show(this@Episode, OnUserEarnedRewardListener() {
                                        fun onUserEarnedReward(rewardItem: RewardItem) {
                                        }
                                    })
                                } else {
                                    intent = Intent(this@Episode, Player::class.java)
                                    intent.putExtra("link", data[position].episode?.link)
                                    startActivity(intent)
                                }
                            }
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

    fun AdLoad(link: String) {
        var adRequest = AdRequest.Builder().build()
        RewardedAd.load(this, this@Episode.getString(R.string.rewardedAdUnit), adRequest, object : RewardedAdLoadCallback() {

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
                        intent = Intent(this@Episode, Player::class.java)
                        intent.putExtra("link", link)
                        startActivity(intent)
                        mRewardedAd = null
                    }

                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                        // Called when ad fails to show.
                        intent = Intent(this@Episode, Player::class.java)
                        intent.putExtra("link", link)
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