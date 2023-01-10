package com.franalarza.dragonballfights.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.franalarza.dragonballfights.adapters.HeroesAdapter
import com.franalarza.dragonballfights.databinding.ActivityHeroesBinding
import com.franalarza.dragonballfights.fragments.HeroesListFragment
import com.franalarza.dragonballfights.viewModels.HeroesActivityViewModel

class HeroesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHeroesBinding
    private val viewModel: HeroesActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeroesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.HeroeListContainer.id, HeroesListFragment.newInstance())
                .commitNow()
        }
    }

}