package com.example.daiet

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.daiet.databinding.ActivityFeedbackResultBinding

class FeedbackResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFeedbackResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedbackResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1) 인텐트 데이터
        val photoUriStr = intent.getStringExtra(EXTRA_PHOTO_URI)
        val calories    = intent.getIntExtra(EXTRA_CALORIES, -1)
        val macros      = intent.getStringExtra(EXTRA_MACROS)
        val classes     = intent.getStringArrayListExtra(EXTRA_CLASSIFICATIONS)

        // 2) 사진
        photoUriStr?.let { binding.mealPhoto.setImageURI(Uri.parse(it)) }

        // 3) 텍스트
        if (calories >= 0) binding.calorieText.text = "${calories} kcal"
        if (!macros.isNullOrBlank()) binding.macroText.text = macros

        // 4) 메뉴 분류 결과
        populateClassification(classes ?: defaultClassifications())

        // 5) 홈으로
        binding.btnGoHome.setOnClickListener {
            val i = Intent(this, HomeActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(i)
            finish()
        }
    }

    private fun populateClassification(items: List<String>) {
        val container = binding.classificationContainer
        container.removeAllViews()

        items.forEachIndexed { index, line ->
            val tv = TextView(this).apply {
                text = "• $line"
                setTextColor(ContextCompat.getColor(this@FeedbackResultActivity, android.R.color.darker_gray))
                textSize = 16f
            }
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                if (index != items.lastIndex) bottomMargin = 8.dp()
            }
            tv.layoutParams = lp
            container.addView(tv)
        }
    }

    private fun defaultClassifications(): List<String> = listOf(
        "한식 / 비빔밥 (신뢰도 0.92)",
        "한식 / 잡채 (신뢰도 0.81)",
        "분식 / 김밥 (신뢰도 0.74)"
    )

    // dp → px
    private fun Int.dp(): Int = (this * resources.displayMetrics.density).toInt()

    companion object {
        const val EXTRA_PHOTO_URI = "photoUri"
        const val EXTRA_CALORIES = "calories"
        const val EXTRA_MACROS = "macros"
        const val EXTRA_CLASSIFICATIONS = "classifications"
    }
}
