package com.example.moodbasedmusicapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide



class SongAdapter(
private val context: Context,
private val songs: List<Song>
) : RecyclerView.Adapter<SongAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnailImageView: ImageView = itemView.findViewById(R.id.songThumbnail)
        val titleTextView: TextView = itemView.findViewById(R.id.songTitle)

        fun bind(song: Song) {
            titleTextView.text = song.title

            Glide.with(context)
                .load(song.thumbnailUrl)
                .centerCrop()
                .placeholder(android.R.color.darker_gray)
                .into(thumbnailImageView)

            itemView.setOnClickListener {
                val intent = Intent(context, VideoPlayerActivity::class.java)
                intent.putExtra("VIDEO_URL", song.url)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_song, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val song = songs[position]
        holder.bind(song)
    }

    override fun getItemCount() = songs.size
}
