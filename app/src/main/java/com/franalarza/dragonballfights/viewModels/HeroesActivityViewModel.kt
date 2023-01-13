package com.franalarza.dragonballfights.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.franalarza.dragonballfights.models.HeroLive
import com.franalarza.dragonballfights.models.Heroe
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException

class HeroesActivityViewModel : ViewModel() {

    var heroesList: List<HeroLive>? = null
    val heroesState: MutableLiveData<HeroesActivityViewModel.HeroesActivityState> by lazy {
        MutableLiveData<HeroesActivityViewModel.HeroesActivityState>()
    }

    fun getHeroesList(hero: String = "", token: String) {
        setValueOnMainThread(HeroesActivityState.Loading)
        val client = OkHttpClient()
        val url = "https://dragonball.keepcoding.education/api/heros/all"
        val body = FormBody.Builder()
            .add("name", hero)
            .build()
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $token")
            .post(body)
            .build()
        val call = client.newCall(request)
        call.enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                setValueOnMainThread(HeroesActivityState.FailureHeroList("Hero list could not be loaded"))
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                val heroesResponse: Array<Heroe> = Gson().fromJson(responseBody, Array<Heroe>::class.java)
                var heroes = heroesResponse.map {
                    HeroLive(it.id, it.name, it.description, it.photo, energy = 100)
                }
                setValueOnMainThread(HeroesActivityState.SuccessHeroList(heroes))
                heroesList = heroes
                Log.d("Heroes", heroesList.toString())
            }

        })

    }

    private fun setValueOnMainThread(newValue: HeroesActivityViewModel.HeroesActivityState) {
        viewModelScope.launch(Dispatchers.Main) {
            heroesState.value = newValue
        }
    }

    sealed class HeroesActivityState {
        object Loading : HeroesActivityState()
        data class SuccessHeroList(val heroList: List<HeroLive>) : HeroesActivityState()
        data class FailureHeroList(val error: String) : HeroesActivityState()
    }
}