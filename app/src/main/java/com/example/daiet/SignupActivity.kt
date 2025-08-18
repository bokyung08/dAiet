package com.example.daiet

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.daiet.databinding.ActivitySignupBinding
import com.example.daiet.repo.BackendRepo
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private var selectedDiseaseCard: MaterialCardView? = null
    private var selectedDiseaseKey: String? = null   // 백엔드로 보낼 질환 코드(null = 없음)

    private val repo by lazy { BackendRepo(this) }

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
        diseaseCards.forEach { card ->
            card.setOnClickListener {
                // 이전 선택 해제
                selectedDiseaseCard?.setCardBackgroundColor(colorOf(android.R.color.white))
                selectedDiseaseCard?.setStrokeColor(colorOf(R.color.unselected_card_stroke))

                // 현재 선택 표시
                card.setCardBackgroundColor(colorOf(R.color.selected_card_background))
                card.setStrokeColor(colorOf(R.color.selected_card_stroke))
                selectedDiseaseCard = card

                // 카드 id → 질환 코드 매핑
                selectedDiseaseKey = mapDiseaseFromCardId(card.id)

                // (참고) 필요하면 카드 안의 텍스트 가져오기
                // val label = (card.getChildAt(0) as? TextView)?.text?.toString()
            }
        }

        binding.signupButton.setOnClickListener {
            val name = binding.nameInput.text?.toString()?.trim().orEmpty()
            val email = binding.emailInput.text?.toString()?.trim().orEmpty()
            val password = binding.passwordInput.text?.toString()?.trim().orEmpty()

            // 성별 선택 (라디오 버튼으로 추가해둘 것)
            val gender = when (binding.genderGroup.checkedRadioButtonId) {
                R.id.rbMale -> "male"
                R.id.rbFemale -> "female"
                else -> null
            }

            // 기본 유효성 검사
            if (email.isEmpty() || password.isEmpty()) {
                toast("이메일/비밀번호를 입력하세요")
                return@setOnClickListener
            }
            if (gender == null) {
                toast("성별을 선택해 주세요")
                return@setOnClickListener
            }

            setLoading(true)
            lifecycleScope.launch {
                try {
                    // 1) 회원가입
                    repo.signup(
                        email = email,
                        password = password,
                        name = name.ifBlank { null },
                        gender = gender
                    )

                    // 2) 자동 로그인(토큰 저장)
                    repo.login(email, password)

                    // 3) 질환 선택이 있으면 등록
                    selectedDiseaseKey?.let { disease ->
                        // 백엔드 chronic POST 호출 (이름만 보내도 됨)
                        repo.addChronic(name = disease, details = null, diagnosedAt = null)
                    }

                    toast("회원가입 완료! 로그인되었습니다.")
                    startActivity(Intent(this@SignupActivity, HomeActivity::class.java))
                    finish()

                } catch (e: HttpException) {
                    toast("회원가입 실패: ${e.code()} ${e.message()}")
                } catch (e: IOException) {
                    toast("네트워크 오류: ${e.message}")
                } catch (e: Exception) {
                    toast("오류: ${e.message}")
                } finally {
                    setLoading(false)
                }
            }
        }
    }

    private fun mapDiseaseFromCardId(id: Int): String? {
        // 백엔드 ChronicCondition.name 에 맞는 키값으로 매핑
        // null 이면 "질환 없음"
        return when (id) {
            R.id.cardNone -> null
            R.id.cardHighbp -> "hypertension"   // 고혈압
            R.id.cardDiabetes -> "diabetes"     // 당뇨
            R.id.cardCholesterol -> "dyslipidemia" // 이상지질혈증(콜레스테롤)
            else -> null
        }
    }

    private fun colorOf(resId: Int) = ContextCompat.getColor(this, resId)

    private fun setLoading(loading: Boolean) {
        binding.signupButton.isEnabled = !loading
        binding.progressBar?.apply {
            visibility = if (loading) android.view.View.VISIBLE else android.view.View.GONE
        }
    }

    private fun toast(msg: String) =
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
