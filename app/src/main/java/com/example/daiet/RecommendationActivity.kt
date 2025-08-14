package com.example.daiet

import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.daiet.databinding.ActivityRecommendationBinding
import com.google.android.material.card.MaterialCardView

class RecommendationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecommendationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO: 백엔드에서 받은 실제 분석 결과로 교체하세요.
        val analysisResult = "최근 식단 10개를 분석했어요.\\n탄수화물 부족, 지방 과다 경향이 있어요."
        binding.recommendResult.text = analysisResult

        // TODO: 백엔드에서 받은 실제 추천 식단 목록으로 교체하세요.
        val recommendedMeals = listOf("닭가슴살 샐러드", "연어 스테이크", "채소 볶음밥")
        displayRecommendedMeals(recommendedMeals)

        // '레시피 보러가기' 버튼 클릭 리스너 설정
        binding.viewRecipeButton.setOnClickListener {
            // TODO: 레시피 목록을 보여주는 새로운 액티비티로 이동하거나 로직을 추가
            Toast.makeText(this, "레시피를 보러 갑니다!", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 추천 식단 목록을 동적으로 생성하여 레이아웃에 추가하는 함수
     * 하드코딩된 값을 사용하여 별도 리소스 파일 없이 동작합니다.
     */
    private fun displayRecommendedMeals(mealList: List<String>) {
        val container = binding.recommendationContainer
        container.removeAllViews() // 기존 뷰가 있다면 모두 제거

        // 레이아웃의 마진, 패딩, 크기를 위한 하드코딩된 값
        val cardWidth = 250.dpToPx()
        val cardHeight = 250.dpToPx()
        val cardMargin = 16.dpToPx()
        val cornerRadius = 16.dpToPx().toFloat()
        val elevation = 8.dpToPx().toFloat()

        for (mealName in mealList) {
            // MaterialCardView 생성
            val cardView = MaterialCardView(this).apply {
                layoutParams = LinearLayout.LayoutParams(cardWidth, cardHeight).apply {
                    marginEnd = cardMargin
                }
                radius = cornerRadius
            }

            // 카드뷰 내부에 들어갈 LinearLayout 생성
            val innerLayout = LinearLayout(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
                orientation = LinearLayout.VERTICAL
            }

            // 이미지 뷰 생성 (플레이스홀더 이미지 대신 배경색 사용)
            val imageView = ImageView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0,
                    1.0f // LinearLayout의 남는 공간을 모두 차지
                )
                scaleType = ImageView.ScaleType.CENTER_CROP
                setBackgroundColor(resources.getColor(android.R.color.darker_gray))
                contentDescription = "$mealName 이미지"
            }

            // 텍스트 뷰 생성
            val textView = TextView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                text = mealName
                textSize = 16f
                gravity = Gravity.CENTER
                setTypeface(null, android.graphics.Typeface.BOLD)
                setTextColor(resources.getColor(android.R.color.black))
                setPadding(8, 8, 8, 8)
            }

            // 뷰들을 계층적으로 추가
            innerLayout.addView(imageView)
            innerLayout.addView(textView)
            cardView.addView(innerLayout)

            // 최종적으로 컨테이너에 카드뷰 추가
            container.addView(cardView)

            // 카드뷰 클릭 리스너 설정
            cardView.setOnClickListener {
                Toast.makeText(this, "$mealName 레시피로 이동합니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * DP 단위를 픽셀로 변환하는 확장 함수
     */
    private fun Int.dpToPx(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }
}
