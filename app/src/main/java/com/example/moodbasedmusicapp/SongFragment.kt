package com.example.moodbasedmusicapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SongFragment : Fragment() {

    private lateinit var emotionTextView: TextView
    private lateinit var playlistHeading: TextView
    private lateinit var songRecyclerView: RecyclerView
    private lateinit var back: ImageButton

    private var mood: String? = null
    private var songs: ArrayList<Song> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mood = arguments?.getString("mood")

        val titles = arguments?.getStringArrayList("titles")
        val urls = arguments?.getStringArrayList("urls")
        val thumbnails = arguments?.getStringArrayList("thumbnails")
        if (titles != null && urls != null && thumbnails != null) {
            for (i in titles.indices) {
                songs.add(Song(titles[i], urls[i], thumbnails[i]))
            }
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_song, container, false)

        emotionTextView = view.findViewById(R.id.emotionText)
        playlistHeading = view.findViewById(R.id.playlistHeading)
        songRecyclerView = view.findViewById(R.id.songView)
        back = view.findViewById(R.id.btnback)

        emotionTextView.text = "Detected Mood: $mood"

        songRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = SongAdapter(requireContext(), songs)   // bas do hi arguments
        songRecyclerView.adapter = adapter

        songRecyclerView.adapter = adapter

        back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        return view
    }

    companion object {
        fun newInstance(mood: String, songs: List<Song>): SongFragment {
            val fragment = SongFragment()
            val titles = ArrayList<String>()
            val urls = ArrayList<String>()
            val thumbnails = ArrayList<String>()

            for (song in songs) {
                titles.add(song.title)
                urls.add(song.url)
                thumbnails.add(song.thumbnailUrl)
            }

            val bundle = Bundle().apply {
                putString("mood", mood)
                putStringArrayList("titles", titles)
                putStringArrayList("urls", urls)
                putStringArrayList("thumbnails", thumbnails)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}
