package com.example.daiet

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.daiet.databinding.ActivityCameraBinding

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.captureButton.setOnClickListener {
            // TODO: 실제 촬영/갤러리에서 얻은 사진 Uri로 교체
            val photoUri: Uri? = null
            startAnalyzing(photoUri)
        }
    }

    private fun startAnalyzing(photoUri: Uri?) {
        val intent = Intent(this, AnalyzingActivity::class.java).apply {
            // 분석 후 이동 대상: feedback (→ FeedbackResultActivity)
            putExtra(AnalyzingActivity.EXTRA_NEXT, "feedback")
            // 사진 URI 전달(있을 때만)
            photoUri?.let { putExtra(FeedbackResultActivity.EXTRA_PHOTO_URI, it.toString()) }
        }
        startActivity(intent)
    }
}
