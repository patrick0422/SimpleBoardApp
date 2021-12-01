package com.example.simpleboardapp.ui.user.register

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.simpleboardapp.R
import com.example.simpleboardapp.data.user.register.RegisterRequest
import com.example.simpleboardapp.databinding.FragmentRegisterBinding
import com.example.simpleboardapp.ui.user.UserViewModel
import com.example.simpleboardapp.util.BaseFragment
import com.example.simpleboardapp.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(R.layout.fragment_register) {
    private val userViewModel: UserViewModel by viewModels()
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun init() {
        binding.viewModel = registerViewModel

        binding.buttonRegister.setOnClickListener {
            onRegister()
        }

        binding.textLogin.setOnClickListener {
            it.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun getRegisterRequest(): RegisterRequest = with(binding) {
        RegisterRequest(
            email = editEmail.text.toString(),
            password = editPassword.text.toString(),
            nickname = editNick.text.toString()
        )
    }

    private fun onRegister() {
        val registerRequest = getRegisterRequest()
        val confirmPassword = binding.editConfirmPassword.text.toString()

        with(registerRequest) {
            if (email.isBlank() || password.isBlank() || confirmPassword.isBlank() || nickname.isBlank()) {
                showToast("정보를 모두 입력해주세요.")
                return
            } else if (password.length < 8) {
                showToast("비밀번호는 8자 이상이어야 합니다.")
                return
            } else if (password != confirmPassword) {
                showToast("비밀번호가 일치하지 않습니다.")
                return
            }
        }

        registerViewModel.register(registerRequest)
        registerViewModel.registerResponse.observe(this, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    loading(false)

                    val userToken = response.data!!.token
                    userViewModel.saveUserToken(userToken)
                    showToast("회원가입 성공! Token: $userToken")
                }
                is NetworkResult.Error -> {
                    loading(false)
                    showToast("회원가입 실패! Message: ${response.message}")
                }
                is NetworkResult.Loading -> {
                    loading(true)
                }
            }
        })
    }

    private fun loading(boolean: Boolean) {
        binding.progressBar.visibility = if (boolean) View.VISIBLE else View.GONE
        binding.buttonRegister.isClickable = !boolean
    }
}