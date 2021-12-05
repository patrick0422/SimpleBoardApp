package com.example.simpleboardapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.simpleboardapp.R
import com.example.simpleboardapp.databinding.ActivityMainBinding
import com.example.simpleboardapp.ui.user.UserActivity
import com.example.simpleboardapp.util.BaseActivity
import com.example.simpleboardapp.util.Constants.Companion.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val mainViewModel: MainViewModel by viewModels()

    override fun init() {
        if (mainViewModel.user.value == null) {
            mainViewModel.getUser()
            Log.d(TAG, "init: Calling User Info")
        }

        mainViewModel.user.observe(this, { user ->
            Log.d(TAG, "init-observe: $user")
            if (user.token.isBlank()) {
                startActivity(Intent(this, UserActivity::class.java))
                finish()
            }
        })
    }
}