package com.example.simpleboardapp.ui.user.login

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.simpleboardapp.R
import com.example.simpleboardapp.data.user.login.LoginRequest
import com.example.simpleboardapp.databinding.FragmentLoginBinding
import com.example.simpleboardapp.ui.main.MainActivity
import com.example.simpleboardapp.ui.user.UserViewModel
import com.example.simpleboardapp.util.BaseFragment
import com.example.simpleboardapp.util.Constants.Companion.TAG
import com.example.simpleboardapp.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    private val userViewModel: UserViewModel by activityViewModels()
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

        if (email.isBlank() || password.isBlank()) {
            showToast("정보를 모두 입력해주세요.")
            return
        }

        isLoading(true)
        loginViewModel.login(LoginRequest(email, password))

        loginViewModel.loginResponse.observe(this, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    isLoading(false)

                    val userToken = response.data!!.token
                    userViewModel.saveUserToken(userToken)

                    showToast("로그인 성공! Token: $userToken")
                    startActivity(Intent(context, MainActivity::class.java))
                    activity?.finish()
                }
                is NetworkResult.Error -> {
                    isLoading(false)

                    if (response.message!!.contains("End of input at line 1 column 1 path \$")) {
                        showToast("계정 정보를 다시 확인해주세요.")
                    } else {
                        Log.d(TAG, "onLogin: ${response.message}")
                    }
                }
                is NetworkResult.Loading -> {
                    isLoading(true)
                }
            }
        })
    }

    private fun isLoading(boolean: Boolean) {
        binding.progressBar.visibility = if (boolean) View.VISIBLE else View.GONE
        binding.buttonLogin.isClickable = !boolean
    }
}