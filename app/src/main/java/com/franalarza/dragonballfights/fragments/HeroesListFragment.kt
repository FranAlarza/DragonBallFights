package com.franalarza.dragonballfights.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.franalarza.dragonballfights.R
import com.franalarza.dragonballfights.adapters.HeroesAdapter
import com.franalarza.dragonballfights.databinding.ActivityHeroesBinding
import com.franalarza.dragonballfights.databinding.FragmentHeroesListBinding
import com.franalarza.dragonballfights.models.Heroe


class HeroesListFragment : Fragment() {

    companion object {
        fun newInstance() = HeroesListFragment()
    }

    private var heroes = listOf(
        Heroe("1", "Goku", "El Masca", "photo-1"),
        Heroe("2", "Trunks", "Mato a freezer y ya", "photo-2"))

    private lateinit var binding: FragmentHeroesListBinding

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
        binding.rwHeroes.adapter = HeroesAdapter(heroes)
    }
}