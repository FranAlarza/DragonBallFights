package com.franalarza.dragonballfights.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.franalarza.dragonballfights.databinding.ActivityLoginBinding
import com.franalarza.dragonballfights.viewModels.LoginActivityViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.editTextLoginName?.setSelectAllOnFocus(true)
        setListeners()
        setLoginState()
    }

    private fun setListeners() {
        binding.LoginButton?.setOnClickListener {
            val user = binding.editTextLoginName?.text.toString()
            val password = binding.editTextLoginPassword?.text.toString()
            viewModel.getToken(user, password)
            val intent = Intent(this, HeroesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setLoginState() {
        viewModel.loginState.observe(this) {
            when (it) {
                is LoginActivityViewModel.LoginActivityState.Loading -> {
                    binding.progressBar?.visibility = View.VISIBLE
                }

                is LoginActivityViewModel.LoginActivityState.ErrorToken -> {
                    binding.progressBar?.visibility = View.GONE
                }

                is LoginActivityViewModel.LoginActivityState.SuccessToken -> {
                    binding.progressBar?.visibility = View.GONE
                }
            }
        }
    }
}