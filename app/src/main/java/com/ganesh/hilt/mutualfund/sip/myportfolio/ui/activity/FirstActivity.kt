package com.ganesh.hilt.mutualfund.sip.myportfolio.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewTreeObserver
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ganesh.hilt.mutualfund.sip.myportfolio.databinding.ActivityFirstBinding
import com.ganesh.hilt.mutualfund.sip.myportfolio.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstActivity : BaseActivity() {

    private val binding by lazy { ActivityFirstBinding.inflate(layoutInflater) }
    var finishSplashScreen: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        if (Build.VERSION.SDK_INT < 31) setContentView(binding.root)

        // Set up an OnPreDrawListener to the root view.
        val content: View = findViewById(android.R.id.content)
        content.getViewTreeObserver()
            .addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // Check if the initial data is ready.
                    if (finishSplashScreen) {
                        // The content is ready; start drawing.
                        content.getViewTreeObserver().removeOnPreDrawListener(this)

                        startActivity(Intent(this@FirstActivity, MainActivity::class.java))

                        return true
                    } else {
                        // The content is not ready; suspend.
                        return false
                    }
                }
            })


        // 5 seconds timeout to hide splash screen
        Handler(Looper.getMainLooper()).postDelayed({ finishSplashScreen = true }, 4000)
    }
}