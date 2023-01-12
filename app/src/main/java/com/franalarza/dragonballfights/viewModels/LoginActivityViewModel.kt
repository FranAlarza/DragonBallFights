package com.franalarza.dragonballfights.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.Base64

class LoginActivityViewModel : ViewModel() {

    var token = ""
    val loginState: MutableLiveData<LoginActivityViewModel.LoginActivityState> by lazy {
        MutableLiveData<LoginActivityViewModel.LoginActivityState>()
    }

    fun getToken(user: String, password: String) {
        setValueOnMainThread(LoginActivityViewModel.LoginActivityState.Loading)
        val client = OkHttpClient()
        val url = "https://dragonball.keepcoding.education/api/auth/login"
        val auth = "$user:$password"
        val authHeader = Base64.getEncoder().encodeToString(auth.toByteArray())

        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Basic $authHeader")
            .post("".toRequestBody())
            .build()
        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                setValueOnMainThread(LoginActivityViewModel.LoginActivityState.ErrorToken(e.message.toString()))
                Log.e("Error en el token", e.message.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val responseToken = response.body?.string()
                Log.e("RESPONSE", response.code.toString())
                if (response.code == 200) {
                    responseToken?.let {
                        setValueOnMainThread(
                            LoginActivityViewModel.LoginActivityState.SuccessToken(it))
                        token = it
                        Log.d("TOKEN", token)
                    }
                } else {
                    setValueOnMainThread(LoginActivityViewModel.LoginActivityState.ErrorToken("error"))
                    Log.e("Error en el token", responseToken ?: "")
                }

            }
        })
    }


    fun setValueOnMainThread(newValue: LoginActivityViewModel.LoginActivityState) {
        viewModelScope.launch(Dispatchers.Main) {
            loginState.value = newValue
        }
    }

    sealed class LoginActivityState {
        object Loading : LoginActivityState()
        data class SuccessToken(val token: String?) : LoginActivityState()
        data class ErrorToken(val message: String) : LoginActivityState()
    }

    fun validateEmailAndPassword(user: String, password: String): Boolean {
        return user.isNotEmpty() && password.isNotEmpty()
    }

}