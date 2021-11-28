package com.example.simpleboardapp.ui.user.login

import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.simpleboardapp.R
import com.example.simpleboardapp.data.user.login.LoginRequest
import com.example.simpleboardapp.databinding.FragmentLoginBinding
import com.example.simpleboardapp.util.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun init() {
        binding.viewModel = loginViewModel

        binding.buttonLogin.setOnClickListener {
            onLogin()
        }

        binding.textRegister.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        loginViewModel.loginResponse.observe(this, { result ->
            if (result.message.contains("Succeed!")) {
                showToast("로그인 성공! Token: ${result.response!!.token}")
                onLoginSucceed()
            } else {
                showToast("로그인 실패! Message: ${result.message}")
            }
        })
    }

    private fun onLoginSucceed() {

    }

    private fun onLogin() {
        val email = binding.editEmail.text.toString()
        val password = binding.editPassword.text.toString()

        // TODO: Handle email, password input (Check their value isn't empty or don't match rule)
        if (email.isBlank() || password.isBlank())
            showToast("정보를 모두 입력해주세요.")

        val loginRequest = LoginRequest(email, password)

        loginViewModel.login(loginRequest)
    }
}