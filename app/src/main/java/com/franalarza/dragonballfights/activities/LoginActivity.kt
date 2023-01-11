package com.franalarza.dragonballfights.activities

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.franalarza.dragonballfights.R
import com.franalarza.dragonballfights.databinding.ActivityLoginBinding
import com.franalarza.dragonballfights.utils.DataStore
import com.franalarza.dragonballfights.viewModels.LoginActivityViewModel
import kotlin.properties.Delegates

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginActivityViewModel by viewModels()
    private lateinit var dataStore: DataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataStore = DataStore(this)
        setListeners()
        setLoginState()
    }

    private fun setListeners() {
        binding.LoginButton?.setOnClickListener {
            val user = binding.editTextLoginName?.text.toString()
            val password = binding.editTextLoginPassword?.text.toString()
            viewModel.getToken(user, password)
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
                    binding.editTextLoginName?.backgroundTintList =
                        ContextCompat.getColorStateList(this, R.color.red)
                    binding.editTextLoginPassword?.backgroundTintList =
                        ContextCompat.getColorStateList(this, R.color.red)
                }

                is LoginActivityViewModel.LoginActivityState.SuccessToken -> {
                    binding.progressBar?.visibility = View.GONE
                    navigateToHeroesList()
                }
            }
        }
    }

    private fun navigateToHeroesList() {
        val intent = Intent(this, HeroesActivity::class.java)
        startActivity(intent)
    }

}