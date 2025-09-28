package com.example.moodbasedmusicapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class VideoPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)
enableEdgeToEdge()
        // Intent se song ka URL receive karo
        val videoUrl = intent.getStringExtra("VIDEO_URL") ?: ""
        val videoId = extractVideoId(videoUrl)

        val youTubePlayerView: YouTubePlayerView = findViewById(R.id.youtubePlayerView)
        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                if (videoId.isNotEmpty()) {
                    youTubePlayer.loadVideo(videoId, 0f)
                }
            }
        })
    }

    // Helper function: URL se videoId nikalna
    private fun extractVideoId(url: String): String {
        return when {
            url.contains("v=") -> url.substringAfter("v=").substringBefore("&")
            url.contains("youtu.be/") -> url.substringAfter("youtu.be/")
            else -> ""
        }
    }
}
