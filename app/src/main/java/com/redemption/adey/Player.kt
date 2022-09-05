package com.redemption.adey

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerFullScreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerInitListener
import kotlinx.android.synthetic.main.activity_player.*

class Player : AppCompatActivity() {

//    private var player: SimpleExoPlayer? = null
//    private var playWhenReady = true
//    private var currentWindow = 0
//    private var playbackPosition : Long = 0
//    private lateinit var videoPlayer: com.google.android.exoplayer2.ui.PlayerView
//    private lateinit var videoid: String
    private lateinit var youtubePlayerView: YouTubePlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

//        initPlayer()

        val videoId: String = intent.getStringExtra("videoId").toString()
        val title: String = intent.getStringExtra("title").toString()
        val description: String = intent.getStringExtra("description").toString()

        youtubePlayerView = findViewById(com.redemption.adey.R.id.youtube_player_view)
        lifecycle.addObserver(youtubePlayerView)

        youtubePlayerView.initialize(object : YouTubePlayerInitListener {
            override fun onInitSuccess(@NonNull initializedYouTubePlayer: YouTubePlayer) {
                initializedYouTubePlayer.addListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady() {
                        initializedYouTubePlayer.loadVideo(videoId, 0F)
                    }
                })
            }
        }, true)

        youtubePlayerView.addFullScreenListener()

        videoTitle.setText(title)
        videoDescription.setText(description)
    }

    override fun onConfigurationChanged(_newConfig: Configuration) {
        super.onConfigurationChanged(_newConfig)
        if (_newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            youtubePlayerView.enterFullScreen();
        } else {
            youtubePlayerView.exitFullScreen();
        }
    }

//    private fun initPlayer() {
//        player = SimpleExoPlayer.Builder(this).build()
//        videoPlayer.player = player
//
//        val videoId = intent.getStringExtra("videoId")
//
//        val videoUrl = "https://www.youtube.com/watch?v=${videoId}"
//        object : YouTubeExtractor(this){
//            override fun onExtractionComplete(
//                ytFiles: SparseArray<YtFile>?,
//                videoMeta: VideoMeta?
//            ) {
//                if(ytFiles != null) {
//                    val itag = 137
//                    val audioTag = 140
//                    val videoUrl = ytFiles[itag].url
//                    val audioUrl = ytFiles[audioTag].url
//
//                    val audioSource : MediaSource = ProgressiveMediaSource
//                        .Factory(DefaultHttpDataSource.Factory())
//                        .createMediaSource(MediaItem.fromUri(audioUrl))
//
//                    val videoSource : MediaSource = ProgressiveMediaSource
//                        .Factory(DefaultHttpDataSource.Factory())
//                        .createMediaSource(MediaItem.fromUri(videoUrl))
//
//                    player!!.setMediaSource(MergingMediaSource(
//                        true, videoSource, audioSource), true)
//                    player!!.prepare()
//                    player!!.playWhenReady = playWhenReady
//                    player!!.seekTo(currentWindow, playbackPosition)
//                }
//            }
//        }.extract(videoUrl, false, true)
//    }
//
//    override fun onStart() {
//        super.onStart()
//        if (Util.SDK_INT >= 24)
//            initPlayer()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        if (Util.SDK_INT < 24 || player == null) {
//            initPlayer()
//            hideSystemUi()
//        }
//    }
//
//    private fun hideSystemUi() {
//        videoPlayer.systemUiVisibility = (
//                View.SYSTEM_UI_FLAG_LOW_PROFILE or
//        View.SYSTEM_UI_FLAG_FULLSCREEN or
//        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
//                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
//                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
//                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                )
//    }
//
//    override fun onPause() {
//        if (Util.SDK_INT < 24) releasePlayer()
//        super.onPause()
//    }
//
//    override fun onStop() {
//        if (Util.SDK_INT < 24) releasePlayer()
//        super.onStop()
//    }
//
//    private fun releasePlayer() {
//        if (player != null) {
//            playWhenReady = player!!.playWhenReady
//            playbackPosition = player!!.currentPosition
//            currentWindow = player!!.currentWindowIndex
//            player!!.release()
//            player = null
//        }
//    }
}

private fun YouTubePlayerView.addFullScreenListener() {
    ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
}
