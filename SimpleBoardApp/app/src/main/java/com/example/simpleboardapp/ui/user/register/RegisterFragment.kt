package com.example.simpleboardapp.ui.user.register

import android.content.Intent
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.simpleboardapp.R
import com.example.simpleboardapp.data.user.RegisterRequest
import com.example.simpleboardapp.data.user.User
import com.example.simpleboardapp.databinding.FragmentRegisterBinding
import com.example.simpleboardapp.ui.main.MainActivity
import com.example.simpleboardapp.ui.user.UserViewModel
import com.example.simpleboardapp.util.BaseFragment
import com.example.simpleboardapp.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(R.layout.fragment_register) {
    private val userViewModel: UserViewModel by activityViewModels()
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
                    isLoading(false)

                    val user = with (response.data!!) {
                        User(id, nickname, email, password, token, createdAt)
                    }
                    userViewModel.saveUser(user)

                    showToast("회원가입 성공!")
                    startActivity(Intent(context, MainActivity::class.java))
                    activity?.finish()
                }
                is NetworkResult.Error -> {
                    isLoading(false)
                    showToast("회원가입 실패! Message: ${response.message}")
                }
                is NetworkResult.Loading -> {
                    isLoading(true)
                }
            }
        })
    }

    private fun isLoading(boolean: Boolean) {
        binding.progressBar.visibility = if (boolean) View.VISIBLE else View.GONE
        binding.buttonRegister.isClickable = !boolean
    }
}