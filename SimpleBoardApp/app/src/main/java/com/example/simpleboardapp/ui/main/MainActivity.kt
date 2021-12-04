package com.example.simpleboardapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.simpleboardapp.R
import com.example.simpleboardapp.databinding.ActivityMainBinding
import com.example.simpleboardapp.ui.user.UserActivity
import com.example.simpleboardapp.util.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val mainViewModel: MainViewModel by viewModels()

    override fun init() {
        if (mainViewModel.user.value!!.id == -1) {
            mainViewModel.getUser()
        }

        mainViewModel.user.observe(this, { user ->
            if (user.id == -1) {
                startActivity(Intent(this, UserActivity::class.java))
                finish()
            }
        })
    }
}