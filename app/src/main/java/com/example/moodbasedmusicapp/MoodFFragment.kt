package com.example.moodbasedmusicapp

import android.Manifest
import android.content.pm.PackageManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File
import java.io.IOException


class MoodFFragment : Fragment() {

    private lateinit var captureButton: Button
    private lateinit var capturedImageView: ImageView
    private lateinit var imageFile: File
    private lateinit var imageUri: Uri
    private lateinit var loadingLayout: LinearLayout

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {

            sendImageToServer(imageFile)
        } else {
            Toast.makeText(requireContext(), "Picture not taken", Toast.LENGTH_SHORT).show()
        }
    }

    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            openCamera()
        } else {
            Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_mood, container, false)

        captureButton = view.findViewById(R.id.captureButton)
        capturedImageView = view.findViewById(R.id.capturedImageView)
        loadingLayout = view.findViewById(R.id.loadingLayout) // 👈

        captureButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }

        return view
    }


    private fun openCamera() {
        try {
            imageFile = File(requireContext().cacheDir, "camera_photo.jpg")
            imageUri = FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.fileprovider",
                imageFile
            )
            cameraLauncher.launch(imageUri)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Camera error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendImageToServer(imageFile: File) {
        loadingLayout.visibility = View.VISIBLE // 👈 Show loader

        val client = OkHttpClient.Builder()
            .connectTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            .build()

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "image",
                imageFile.name,
                imageFile.asRequestBody("image/*".toMediaTypeOrNull())
            )
            .build()

        val request = Request.Builder()
            .url("http://192.168.0.105:5000/detect_mood")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                requireActivity().runOnUiThread {
                    loadingLayout.visibility = View.GONE // 👈 Hide loader
                    Toast.makeText(requireContext(), "Failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                requireActivity().runOnUiThread {
                    loadingLayout.visibility = View.GONE // 👈 Hide loader
                }

                if (response.isSuccessful) {
                    val json = JSONObject(response.body?.string() ?: "")
                    val mood = json.optString("mood", "Unknown")
                    val songsJson = json.optJSONArray("songs")
                    val songList = mutableListOf<Song>()

                    songsJson?.let {
                        for (i in 0 until it.length()) {
                            val song = it.getJSONObject(i)
                            val title = song.getString("title")
                            val url = song.getString("url")
                            val thumbnail = song.optString("thumbnail", "")
                            songList.add(Song(title, url, thumbnail))
                        }
                    }

                    requireActivity().runOnUiThread {
                        val fragment = SongFragment.newInstance(mood, songList)
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(null)
                            .commit()
                    }
                } else {
                    requireActivity().runOnUiThread {
                        Toast.makeText(
                            requireContext(),
                            "Server error: ${response.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }

}
