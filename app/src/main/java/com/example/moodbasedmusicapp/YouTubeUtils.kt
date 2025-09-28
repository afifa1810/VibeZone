package com.example.moodbasedmusicapp

import android.net.Uri

object YouTubeUtils {

    /**
     * YouTube URL se videoId nikalta hai.
     * Supports: youtu.be/ID, youtube.com/watch?v=ID, m.youtube.com, youtube.com/shorts/ID
     */
    fun extractVideoId(url: String): String? {
        return try {
            val uri = Uri.parse(url)

            val host = uri.host.orEmpty()
            val path = uri.path.orEmpty()

            when {
                // https://youtu.be/VIDEO_ID
                host.contains("youtu.be") -> uri.lastPathSegment

                // https://www.youtube.com/shorts/VIDEO_ID
                host.contains("youtube.com") && path.startsWith("/shorts/") ->
                    path.removePrefix("/shorts/").substringBefore('/')

                // https://www.youtube.com/watch?v=VIDEO_ID (also m.youtube.com)
                host.contains("youtube.com") || host.contains("m.youtube.com") ->
                    uri.getQueryParameter("v")

                else -> null
            }
        } catch (_: Exception) {
            null
        }
    }
}
