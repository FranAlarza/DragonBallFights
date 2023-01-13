package com.franalarza.dragonballfights.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.franalarza.dragonballfights.R
import com.franalarza.dragonballfights.adapters.HeroesAdapter
import com.franalarza.dragonballfights.databinding.ActivityHeroesBinding
import com.franalarza.dragonballfights.databinding.FragmentHeroesListBinding
import com.franalarza.dragonballfights.models.HeroLive
import com.franalarza.dragonballfights.models.Heroe
import com.franalarza.dragonballfights.viewModels.HeroesActivityViewModel


class HeroesListFragment() : Fragment() {

    private lateinit var binding: FragmentHeroesListBinding
    private val viewModel: HeroesActivityViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHeroesListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createRecycler()
    }

    private fun createRecycler() {
        binding.rwHeroes.layoutManager = LinearLayoutManager(this.context)
        binding.rwHeroes.adapter = viewModel.heroesList?.let { HeroesAdapter(it) }
    }
}