package com.example.simpleboardapp.util

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<B : ViewDataBinding>(@LayoutRes private val layoutResId: Int) : AppCompatActivity() {
    lateinit var binding: B
    private var waitTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResId)
        binding.lifecycleOwner = this@BaseActivity

        init()
    }

    abstract fun init()

//    override fun onBackPressed() {
//        if (System.currentTimeMillis() - waitTime >= 1500) {
//            waitTime = System.currentTimeMillis()
//            makeToast("뒤로가기 버튼을 한번 더 누르면 종료됩니다")
//        } else finish()
//    }

    protected fun makeToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}