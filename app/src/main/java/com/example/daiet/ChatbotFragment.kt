package com.example.daiet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.daiet.databinding.FragmentChatbotBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChatbotFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentChatbotBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatbotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 챗봇 보내기 버튼 리스너
        binding.sendButton.setOnClickListener {
            val userInput = binding.userInput.text.toString()
            if (userInput.isNotEmpty()) {
                binding.chatDisplay.append("나: $userInput\n")
                binding.userInput.text.clear()
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): BottomSheetDialog {
        // BottomSheetDialog의 크기를 커스터마이징합니다.
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as LinearLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)

            // 화면 높이의 2/3 계산
            val displayMetrics = requireContext().resources.displayMetrics
            val screenHeight = displayMetrics.heightPixels
            val peekHeight = (screenHeight * 2) / 3

            // BottomSheet의 최대 높이를 화면 높이의 2/3로 설정
            behavior.peekHeight = peekHeight
            // 프래그먼트가 확장된 상태로 시작하도록 설정
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
