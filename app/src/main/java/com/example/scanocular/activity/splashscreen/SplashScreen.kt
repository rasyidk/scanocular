package com.example.scanocular.activity.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AlphaAnimation
import android.widget.RelativeLayout
import com.example.scanocular.R
import com.example.scanocular.activity.login.LoginActivity
import java.util.Timer
import java.util.TimerTask
@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.duration = 1000
        findViewById<RelativeLayout>(R.id.splash).startAnimation(fadeIn)

        Timer().schedule(object : TimerTask() {
            override fun run() {
                val intent = Intent(this@SplashScreen, LoginActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }
        }, 2000)
    }
}