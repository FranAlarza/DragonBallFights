package com.franalarza.dragonballfights.viewModels

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import okio.ByteString.Companion.decodeBase64
import java.io.IOException
import java.util.Base64

class LoginActivityViewModel: ViewModel() {

    private var token = ""

    val loginState : MutableLiveData<LoginActivityState> by lazy {
        MutableLiveData<LoginActivityState>()
    }

    fun getToken(user: String, password: String) {
        setValueOnMainThread(LoginActivityState.Loading)
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
                setValueOnMainThread(LoginActivityState.ErrorToken(e.message.toString()))
                Log.e("Error en el token", e.message.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val responseToken = response.body?.string()
                responseToken?.let {
                    setValueOnMainThread(LoginActivityState.SuccessToken(it))
                    token = it
                    Log.d("Token", token)
                }
            }
        })
    }

    fun setValueOnMainThread(newValue: LoginActivityState) {
        viewModelScope.launch(Dispatchers.Main) {
            loginState.value = newValue
        }
    }

    sealed class LoginActivityState {
        object Loading : LoginActivityState()
        data class SuccessToken(val token: String?) : LoginActivityState()
        data class ErrorToken(val message: String) : LoginActivityState()
    }
}