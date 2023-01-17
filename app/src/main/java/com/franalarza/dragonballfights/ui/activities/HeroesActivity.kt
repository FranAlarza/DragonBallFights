package com.franalarza.dragonballfights.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.franalarza.dragonballfights.R
import com.franalarza.dragonballfights.ui.adapters.HeroesAdapter
import com.franalarza.dragonballfights.databinding.ActivityHeroesBinding
import com.franalarza.dragonballfights.ui.fragments.BattleFragment
import com.franalarza.dragonballfights.ui.fragments.HeroesListFragment
import com.franalarza.dragonballfights.models.HeroLive
import com.franalarza.dragonballfights.utils.DataStore
import com.franalarza.dragonballfights.viewModels.HeroesActivityViewModel
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView

interface CallBackHeroFighters {
    fun passFighters(heroesToFight: MutableList<HeroLive>)
}

class HeroesActivity : AppCompatActivity(), CallBackHeroFighters {
    private lateinit var binding: ActivityHeroesBinding
    private val viewModel: HeroesActivityViewModel by viewModels()
    private lateinit var navBar: BottomNavigationView
    private var fighters = mutableListOf<HeroLive>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeroesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navBar = binding.navigationBar
        getHeroesList()
        setListeners()
        setObservers()
    }

    private fun setObservers() {
        viewModel.heroesState.observe(this) { it ->
            when (it) {
                is HeroesActivityViewModel.HeroesActivityState.Loading -> {
                    binding.pbHeroes.visibility = View.VISIBLE
                }
                is HeroesActivityViewModel.HeroesActivityState.FailureHeroList -> {
                    binding.pbHeroes.visibility = View.GONE
                }
                is HeroesActivityViewModel.HeroesActivityState.SuccessHeroList -> {
                    binding.pbHeroes.visibility = View.GONE
                    replaceFragment(HeroesListFragment(it.heroList))
                }
            }
        }
    }

    private fun setListeners() {

        navBar.setOnItemSelectedListener {
            when (it.itemId) {

                R.id.heroesItemNav -> replaceFragment(HeroesListFragment(viewModel.getHeroes()))

                R.id.battleItemNav -> if (fighters.size == 2) {
                    replaceFragment(BattleFragment(fighters))
                } else {
                    Toast.makeText(
                        this,
                        "The battle isn't ready, choose your fighter",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            true
        }
    }

    private fun getHeroesList() {
        val userToken = intent.getStringExtra(DataStore.TOKEN_KEY) ?: ""
        viewModel.getHeroesList(token = userToken)
    }


    private fun replaceFragment(fragment: Fragment) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(binding.HeroeListContainer.id, fragment)
            .commitNow()
    }

    override fun passFighters(heroesToFight: MutableList<HeroLive>) {
        fighters = heroesToFight
        Toast.makeText(
            this,
            "You choose ${heroesToFight[0].name} and your opponent is ${heroesToFight[1].name}",
            Toast.LENGTH_SHORT
        ).show()

    }
}