package com.franalarza.dragonballfights.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.franalarza.dragonballfights.databinding.ActivityLoginBinding
import com.franalarza.dragonballfights.utils.DataStore
import com.franalarza.dragonballfights.viewModels.LoginActivityViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginActivityViewModel by viewModels()
    private lateinit var dataStore: DataStore
    private var userTextField: EditText? = null
    private var passwordTextField: EditText? = null
    private var checkBox: CheckBox? = null
    private val TOKEN_KEY = "token"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataStore = DataStore(this)
        userTextField = binding.editTextLoginName
        passwordTextField = binding.editTextLoginPassword
        checkBox = binding.rememberCheck
        setListeners()
        setLoginStateObserver()
    }

    private fun setListeners() {
        binding.LoginButton?.setOnClickListener {
            val user = userTextField?.text.toString()
            val pass = passwordTextField?.text.toString()
            viewModel.getToken(user, pass)
        }

        passwordTextField?.setOnFocusChangeListener { _, hasFocus ->
           if (hasFocus) passwordTextField?.text = getPassword()
        }

        passwordTextField?.addTextChangedListener { text ->
            if (text?.isNotEmpty() == true) {
                binding.LoginButton?.isEnabled = true
            }
        }
    }

    private fun setLoginStateObserver() {
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
                    val user = userTextField?.text.toString()
                    val pass = passwordTextField?.text.toString()
                    if (checkBox?.isChecked == true) {
                        dataStore.savePassword(user,pass)
                    }
                    navigateToHeroesList()
                }
            }
        }
    }

    private fun navigateToHeroesList() {
        val intent = Intent(this, HeroesActivity::class.java)
        intent.putExtra(DataStore.TOKEN_KEY, viewModel.token)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun getPassword(): Editable? {
        val key = userTextField?.text.toString()
        val password = dataStore.readPassword(key)
        return Editable.Factory.getInstance().newEditable(password)
    }

}