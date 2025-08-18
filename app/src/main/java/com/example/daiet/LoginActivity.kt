package com.example.daiet

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.daiet.databinding.ActivityLoginBinding
import com.example.daiet.repo.BackendRepo
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var repo: BackendRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repo = BackendRepo(applicationContext)

        binding.loginButton.setOnClickListener {
            val email = binding.emailInput.text?.toString()?.trim().orEmpty()
            val password = binding.passwordInput.text?.toString()?.trim().orEmpty()

            // 간단한 입력 검증
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "이메일과 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 버튼 잠그고 진행 표시
            setLoading(true)

            lifecycleScope.launch {
                try {
                    // 서버에 로그인 요청 (성공 시 토큰 저장은 BackendRepo가 처리)
                    repo.login(email, password)

                    // 홈으로 이동 (백스택 제거)
                    startActivity(
                        Intent(this@LoginActivity, HomeActivity::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        }
                    )
                    finish()

                } catch (e: HttpException) {
                    // 서버에서 4xx/5xx 응답 시
                    val msg = when (e.code()) {
                        400, 401 -> "이메일 또는 비밀번호가 올바르지 않습니다."
                        else -> "로그인 실패 (${e.code()})"
                    }
                    Toast.makeText(this@LoginActivity, msg, Toast.LENGTH_SHORT).show()

                } catch (e: IOException) {
                    // 네트워크 문제
                    Toast.makeText(this@LoginActivity, "네트워크 오류. 연결을 확인하세요.", Toast.LENGTH_SHORT).show()

                } catch (e: Exception) {
                    // 그 밖의 예외
                    Toast.makeText(this@LoginActivity, "알 수 없는 오류: ${e.message}", Toast.LENGTH_SHORT).show()

                } finally {
                    setLoading(false)
                }
            }
        }

        binding.signupLink.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    private fun setLoading(loading: Boolean) {
        binding.loginButton.isEnabled = !loading
        binding.emailInput.isEnabled = !loading
        binding.passwordInput.isEnabled = !loading
        binding.loginButton.text = if (loading) "로그인 중..." else "로그인"
    }
}
