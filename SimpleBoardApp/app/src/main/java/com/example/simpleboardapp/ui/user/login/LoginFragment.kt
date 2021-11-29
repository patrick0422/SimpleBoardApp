package com.example.simpleboardapp.ui.user.login

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.simpleboardapp.R
import com.example.simpleboardapp.data.user.login.LoginRequest
import com.example.simpleboardapp.databinding.FragmentLoginBinding
import com.example.simpleboardapp.util.BaseFragment
import com.example.simpleboardapp.util.Constants.Companion.TAG
import com.example.simpleboardapp.util.NetworkResult
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
    }

    private fun onLogin() {
        val email = binding.editEmail.text.toString()
        val password = binding.editPassword.text.toString()

        if (email.isBlank() || password.isBlank())
            showToast("정보를 모두 입력해주세요.")

        showLoading(true)
        loginViewModel.login(LoginRequest(email, password))

        loginViewModel.loginResponse.observe(this, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    showLoading(false)

                    val userToken = response.data!!.token
                    loginViewModel.saveUserToken(userToken)

                    showToast("로그인 성공! Token: $userToken")
                }
                is NetworkResult.Error -> {
                    showLoading(false)

                    Log.d(TAG, "onLogin: ${response.message}")
                }
                is NetworkResult.Loading -> {
                    showLoading(true)
                }
            }
        })
    }

    private fun showLoading(boolean: Boolean) {
        binding.progressBar.visibility = if (boolean) View.VISIBLE else View.GONE
    }
}