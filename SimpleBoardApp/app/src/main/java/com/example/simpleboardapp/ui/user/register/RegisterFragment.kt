package com.example.simpleboardapp.ui.user.register

import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.simpleboardapp.R
import com.example.simpleboardapp.data.user.register.RegisterRequest
import com.example.simpleboardapp.databinding.FragmentRegisterBinding
import com.example.simpleboardapp.util.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(R.layout.fragment_register) {
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun init() {
        binding.viewModel = registerViewModel

        binding.buttonRegister.setOnClickListener {
            onRegister()
        }

        binding.textLogin.setOnClickListener {
            it.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        registerViewModel.registerResponse.observe(this, { result ->
            if (result.message.contains("Succeed!")) {
                showToast("회원가입 성공! Current Time: ${result.response!!.createdAt}")
                onRegisterSucceed()
            } else {
                showToast("회원가입 실패! Message: ${result.message}")
            }
        })
    }

    private fun onRegisterSucceed() {

    }

    private fun onRegister() {
        val email = binding.editEmail.text.toString()
        val password = binding.editPassword.text.toString()
        val confirmPassword = binding.editConfirmPassword.text.toString()
        val nickname = binding.editNick.text.toString()

        if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            showToast("정보를 모두 입력해주세요.")
            return
        } else if (password.length < 8) {
            showToast("비밀번호는 8자 이상이어야 합니다.")
            return
        } else if (password != confirmPassword) {
            showToast("비밀번호가 일치하지 않습니다.")
            return
        }

        val registerRequest = RegisterRequest(email, password, nickname)

        registerViewModel.register(registerRequest)
    }
}