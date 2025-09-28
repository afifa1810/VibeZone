package com.example.moodbasedmusicapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {

    private lateinit var getStartedButton: Button
    private lateinit var imageHeadphones: ImageView
    private lateinit var textMain: TextView
    private lateinit var textSub: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()

        getStartedButton = findViewById(R.id.btnGetStarted)
        imageHeadphones = findViewById(R.id.imageHeadphones)
        textMain = findViewById(R.id.textMain)
        textSub = findViewById(R.id.textSub)

        window.statusBarColor = Color.TRANSPARENT
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false

        val slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val bounce = AnimationUtils.loadAnimation(this, R.anim.bounce)


        imageHeadphones.startAnimation(slideIn)

        imageHeadphones.postDelayed({
            typeWriterEffect(textMain, "Welcome to VibeZone!", 80)
        }, 1500)

        // 3️⃣ subtitle fades in
        textMain.postDelayed({
            textSub.visibility = View.VISIBLE
            textSub.startAnimation(fadeIn)
        }, 3200)

        // 4️⃣ button appears + bounces, then opens AuthActivity on click
        textSub.postDelayed({
            getStartedButton.visibility = View.VISIBLE
            getStartedButton.startAnimation(bounce)

            getStartedButton.setOnClickListener {
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
            }
        }, 4500)
    }

    // Typewriter Effect
    private fun typeWriterEffect(tv: TextView, text: String, delay: Long = 100) {
        tv.visibility = View.VISIBLE
        tv.text = ""
        var i = 0
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                if (i < text.length) {
                    tv.append(text[i].toString())
                    i++
                    handler.postDelayed(this, delay)
                }
            }
        }
        handler.post(runnable)
    }
}
