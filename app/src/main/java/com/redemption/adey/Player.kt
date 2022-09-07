package com.redemption.adey

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerFullScreenListener
import kotlinx.android.synthetic.main.activity_player.*


class Player : AppCompatActivity() {

    private lateinit var youtubePlayerView: YouTubePlayerView
    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        val videoId: String = intent.getStringExtra("videoId").toString()
        val title: String = intent.getStringExtra("title").toString()
        val description: String = intent.getStringExtra("description").toString()

        mAdView = findViewById(R.id.adView)

        youtubePlayerView = findViewById(R.id.youtube_player_view)
        lifecycle.addObserver(youtubePlayerView)

        youtubePlayerView.initialize({ initializedYouTubePlayer ->
            initializedYouTubePlayer.addListener(object : AbstractYouTubePlayerListener() {
                override fun onReady() {
                    initializedYouTubePlayer.loadVideo(videoId, 0F)
                }
            })
        }, true)

        videoTitle.text = title
        videoDescription.text = description

        youtubePlayerView.addFullScreenListener(object : YouTubePlayerFullScreenListener {
            override fun onYouTubePlayerEnterFullScreen() {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                youtubePlayerView.enterFullScreen()
            }

            override fun onYouTubePlayerExitFullScreen() {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                youtubePlayerView.exitFullScreen()
            }
        })

        MobileAds.initialize(this) {}

        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

    }

    override fun onConfigurationChanged(_newConfig: Configuration) {
        super.onConfigurationChanged(_newConfig)
        if (_newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            youtubePlayerView.enterFullScreen()
            mAdView.visibility = View.GONE

            val windowInsetsController =
                ViewCompat.getWindowInsetsController(window.decorView) ?: return
            // Configure the behavior of the hidden system bars
            windowInsetsController.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            // Hide both the status bar and the navigation bar
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())

            supportActionBar?.hide()
        } else {
            youtubePlayerView.exitFullScreen()
            mAdView.visibility = View.VISIBLE

            val windowInsetsController =
                ViewCompat.getWindowInsetsController(window.decorView) ?: return
            // Configure the behavior of the hidden system bars
            windowInsetsController.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            // Hide both the status bar and the navigation bar
            windowInsetsController.show(WindowInsetsCompat.Type.systemBars())

            supportActionBar?.show()
        }
    }

    override fun onBackPressed() {
        if (youtubePlayerView.isFullScreen)
            youtubePlayerView.exitFullScreen()
        else
            super.onBackPressed()
    }
}