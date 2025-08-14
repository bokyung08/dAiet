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
            // TODO: 식단 추천 액티비티로 이동
            startActivity(Intent(this, RecommendationActivity::class.java))
        }

        binding.btnChatbot.setOnClickListener {
            // TODO: 챗봇 UI 호출 (예: BottomSheetDialogFragment)
            val chatbot = ChatbotFragment()
            chatbot.show(supportFragmentManager, "Chatbot")
        }
    }
}
