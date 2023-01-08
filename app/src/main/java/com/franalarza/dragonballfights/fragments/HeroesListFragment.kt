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


class HeroesListFragment : Fragment() {

    private lateinit var binding: FragmentHeroesListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHeroesListBinding.inflate(inflater)
        return binding.root
        createRecycler()
    }

    companion object {
        fun newInstance() = HeroesListFragment()
    }

    private fun createRecycler() {
        binding.rwHeroes.adapter = HeroesAdapter()
        binding.rwHeroes.layoutManager = LinearLayoutManager(this.context)
    }
}