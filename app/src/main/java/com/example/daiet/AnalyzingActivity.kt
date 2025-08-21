
package com.example.daiet

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.daiet.FeedbackResultActivity
import com.example.daiet.R
import com.example.daiet.RecommendationActivity

class AnalyzingActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NEXT = "next"  // "feedback" | "recommend"
        // 사진 전달 키는 기존과 동일하게 FeedbackResultActivity.EXTRA_PHOTO_URI 사용 가능
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analyzing)

        val next = intent.getStringExtra(EXTRA_NEXT) ?: "feedback"

        // 2~3초 로딩 후 분기
        window.decorView.postDelayed({
            if (next == "recommend") {
                startActivity(Intent(this, RecommendationActivity::class.java))
            } else {
                val i = Intent(this, FeedbackResultActivity::class.java).apply {
                    // 필요 시 사진 URI를 이어서 전달
                    val uri = intent.getStringExtra(FeedbackResultActivity.EXTRA_PHOTO_URI)
                    if (uri != null) putExtra(FeedbackResultActivity.EXTRA_PHOTO_URI, uri)
                }
                startActivity(i)
            }
            finish()
        }, 3000L)
    }
}
