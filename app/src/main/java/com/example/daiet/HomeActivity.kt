package com.example.daiet

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.daiet.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFeedback.setOnClickListener {
            startActivity(Intent(this, CameraActivity::class.java))
        }

        binding.btnRecommend.setOnClickListener {
            val intent = Intent(this, AnalyzingActivity::class.java).apply {
                putExtra(AnalyzingActivity.EXTRA_NEXT, "recommend") // ← 문자열 키로 전달
            }
            startActivity(intent)
        }



    }
}
