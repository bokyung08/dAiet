package com.example.daiet

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lifecycleScope.launch {
            // 2초 동안 대기
            delay(2000)

            // LoginActivity로 이동
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)

            // 스플래시 액티비티 종료
            finish()
        }
    }
}
