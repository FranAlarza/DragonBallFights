package com.franalarza.dragonballfights.viewModels

import android.content.Context
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.franalarza.dragonballfights.models.HeroLive
import com.franalarza.dragonballfights.models.Heroe
import com.franalarza.dragonballfights.utils.Constants
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException
import java.util.*

class HeroesActivityViewModel : ViewModel() {

    var heroesList = mutableListOf<HeroLive>()

    val heroesState: MutableLiveData<HeroesActivityState> by lazy {
        MutableLiveData<HeroesActivityState>()
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
                val heroes = heroesResponse.map {
                    HeroLive(it.id, it.name, it.description, it.photo, energy = Constants.maxEnergy, true)
                }
                val filteredHeroes = heroes.filter { it.photo.contains("alfabeta") }.toMutableList()
                setValueOnMainThread(HeroesActivityState.SuccessHeroList(filteredHeroes))

                heroesList = filteredHeroes
            }

        })

    }

    private fun setValueOnMainThread(newValue: HeroesActivityState) {
        viewModelScope.launch(Dispatchers.Main) {
            heroesState.value = newValue
        }
    }

    fun getHeroes(): MutableList<HeroLive> {
        return heroesList
    }

    fun executeRound(fighters: MutableList<HeroLive>, barLifeFirstFighter: ProgressBar, barLifeSecondFighter: ProgressBar, winner: () -> Unit){
        val randomAmount = (10..60).random()
        val randomIndex = (0..1).random()
        if (randomIndex == 0) {
            modifyLifeFighter(fighters[Constants.firstFighter].name, fighters, randomAmount)
            barLifeFirstFighter.progress -= randomAmount
        } else {
            modifyLifeFighter(fighters[Constants.secondFighter].name, fighters, randomAmount)
            barLifeSecondFighter.progress -= randomAmount
        }

        winner()
    }

    private fun modifyLifeFighter(name: String, fighters: MutableList<HeroLive>, newLife: Int) {
        fighters.map {
            if (it.name == name) {
                it.energy -= newLife
            }
        }
    }

    fun setWinner(heroes: MutableList<HeroLive>,winnerMessage: (nameWinner: String) -> Unit) {
        val searchWinner = heroes.filter { it.energy > 0 }

        if (searchWinner.size == 1) {
            winnerMessage(searchWinner.first().name)
        }
    }

    fun restartBattle(fighters: MutableList<HeroLive>, lifeFighterOne: ProgressBar, lifeFighterTwo: ProgressBar) {
        fighters[Constants.firstFighter].energy = Constants.maxEnergy
        fighters[Constants.secondFighter].energy = Constants.maxEnergy

        lifeFighterOne.progress = Constants.maxEnergy
        lifeFighterTwo.progress = Constants.maxEnergy
    }

    fun restartGame(fighters: MutableList<HeroLive>) {
        fighters.map { it.energy = Constants.maxEnergy }
    }


    sealed class HeroesActivityState {
        object Loading : HeroesActivityState()
        data class SuccessHeroList(val heroList: MutableList<HeroLive>) : HeroesActivityState()
        data class FailureHeroList(val error: String) : HeroesActivityState()
    }
}