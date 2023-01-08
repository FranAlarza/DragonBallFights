package com.franalarza.dragonballfights

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.franalarza.dragonballfights.adapters.HeroesAdapter
import com.franalarza.dragonballfights.databinding.ActivityHeroesBinding

class HeroesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHeroesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeroesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}