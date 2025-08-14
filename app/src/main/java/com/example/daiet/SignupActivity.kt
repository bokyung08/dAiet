package com.example.daiet

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.daiet.databinding.ActivitySignupBinding
import com.google.android.material.card.MaterialCardView

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private var selectedDiseaseCard: MaterialCardView? = null
    private var selectedDisease: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 카드 뷰 리스트
        val diseaseCards = listOf(
            binding.cardNone,
            binding.cardHighbp,
            binding.cardDiabetes,
            binding.cardCholesterol
        )

        // 각 카드 뷰에 클릭 리스너 설정
        for (card in diseaseCards) {
            card.setOnClickListener {
                // 이전에 선택된 카드의 배경색을 원래대로 되돌립니다.
                selectedDiseaseCard?.setCardBackgroundColor(resources.getColor(android.R.color.white, null))
                selectedDiseaseCard?.setStrokeColor(resources.getColor(R.color.unselected_card_stroke, null))

                // 현재 클릭된 카드의 배경색과 테두리 색상을 변경합니다.
                card.setCardBackgroundColor(resources.getColor(R.color.selected_card_background, null))
                card.setStrokeColor(resources.getColor(R.color.selected_card_stroke, null))

                // 현재 선택된 카드와 질환명을 업데이트합니다.
                selectedDiseaseCard = card
                val cardText = (card.getChildAt(0) as TextView).text.toString()
                selectedDisease = cardText
            }
        }

        binding.signupButton.setOnClickListener {
            val name = binding.nameInput.text.toString()
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()

            // 이제 선택된 질환 정보는 selectedDisease 변수에 저장됩니다.
            val disease = selectedDisease

            // TODO: 회원가입 처리 로직을 여기에 추가

            // 회원가입 성공 후 LoginActivity로 이동
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // 현재 액티비티를 종료하여 뒤로가기 버튼으로 돌아오지 못하게 함
        }
    }
}
